/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.ingest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import uk.urchinly.wabi.constants.MessagingConstants;

@RestController
@CrossOrigin
public class UploadController {

	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@Value("${app.share.path}/vault")
	private String wabiSharePath;

	@Autowired
	private AssetRepository assetMongoRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {

		if (file.isEmpty()) {
			logger.debug("Upload file is empty.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed with empty file");
		}

		BufferedOutputStream outputStream = null;

		try {
			File outputFile = new File(wabiSharePath + "/" + file.getOriginalFilename());
			outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

			FileCopyUtils.copy(file.getInputStream(), outputStream);

			Asset asset = new Asset(file.getOriginalFilename(), file.getOriginalFilename(), (double) file.getSize(),
					this.parseContentTypeToMimeType(file.getContentType()), Collections.emptyList());

			this.saveAsset(asset);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed with error");
		} finally {
			IOUtils.closeQuietly(outputStream);
		}

		return ResponseEntity.ok("File accepted");
	}

	@Transactional
	private void saveAsset(Asset asset) {

		Asset savedAsset = this.assetMongoRepository.save(asset);

		this.rabbitTemplate.convertAndSend(MessagingConstants.ASSET_INSERT_IMAGE_ROUTE, savedAsset.toString());

		logger.debug("Ingested asset with id {}.", savedAsset.getId());
	}

	private MimeType parseContentTypeToMimeType(String contentType) {
		try {
			return MimeTypeUtils.parseMimeType(contentType);
		} catch (Exception e) {
			logger.debug("Failed to map content type '{}' to a valid mime type.", contentType, e);
			return MimeTypeUtils.APPLICATION_OCTET_STREAM;
		}
	}
}