/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.ingest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import uk.urchinly.wabi.constants.MessagingConstants;
import uk.urchinly.wabi.entities.WabiAsset;
import uk.urchinly.wabi.events.AssetEvent;

@Controller
public class UploadController {

	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@Value("${app.share.path}/vault")
	private String wabiSharePath;

	@Autowired
	private AssetRepository assetMongoRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@RequestMapping(method = RequestMethod.GET, value = "/upload")
	public String provideUploadInfo(Model model) {

		List<WabiAsset> list = this.assetMongoRepository.findByUserId("test");

		model.addAttribute("mongos", list);

		File rootFolder = new File(wabiSharePath);

		List<String> fileNames = Arrays.stream(rootFolder.listFiles()).map(f -> f.getName())
				.collect(Collectors.toList());

		model.addAttribute("files",
				Arrays.stream(rootFolder.listFiles()).sorted(Comparator.comparingLong(f -> -1 * f.lastModified()))
						.map(f -> f.getName()).collect(Collectors.toList()));

		return "uploadForm";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	public String handleFileUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {

		if (name.contains("/")) {
			redirectAttributes.addFlashAttribute("message", "Folder separators not allowed");
			return "redirect:upload";
		}
		if (name.contains("/")) {
			redirectAttributes.addFlashAttribute("message", "Relative pathnames not allowed");
			return "redirect:upload";
		}

		if (!file.isEmpty()) {
			try {
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(wabiSharePath + "/" + name)));
				FileCopyUtils.copy(file.getInputStream(), stream);
				stream.close();

				Asset asset = new Asset(name, file.getOriginalFilename(), (double) file.getSize(),
						Collections.EMPTY_LIST);

				this.saveAsset(asset);

				redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + name + "!");
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message",
						"You failed to upload " + name + " => " + e.getMessage());
			}
		} else {
			redirectAttributes.addFlashAttribute("message",
					"You failed to upload " + name + " because the file was empty");
		}

		return "redirect:upload";
	}

	@Transactional
	private void saveAsset(Asset asset) {
		AssetEvent assetEvent = new AssetEvent();
		BeanUtils.copyProperties(asset, assetEvent);

		Asset savedAsset = this.assetMongoRepository.save(asset);
		this.rabbitTemplate.convertAndSend(MessagingConstants.NEW_ARTICLE_UPLOAD_ROUTE, assetEvent);

		logger.debug("Ingested asset with id {}.", savedAsset.getId());
	}
}