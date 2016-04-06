/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.expose;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uk.urchinly.wabi.constants.MessagingConstants;
import uk.urchinly.wabi.events.UsageEvent;

@RestController
@CrossOrigin
public class DownloadController {

	private static final Logger logger = LoggerFactory.getLogger(DownloadController.class);

	@Value("${app.share.path}/vault")
	private String wabiSharePath;

	@Autowired
	private AssetRepository assetMongoRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@RequestMapping(method = RequestMethod.GET, value = "/assets")
	public Page<Asset> getAssets(@PageableDefault(page = 0, size = 20) Pageable pageable) {

		Page<Asset> assets = this.assetMongoRepository.findAll(pageable);

		return assets;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/assets/{assetId}")
	public Asset getAsset(@PathVariable String assetId) {

		Asset asset = this.assetMongoRepository.findOne(assetId);

		this.rabbitTemplate.convertAndSend(MessagingConstants.USAGE_ROUTE,
				new UsageEvent("view asset", asset.toString()));

		return asset;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/assets/user/{userId}")
	public Page<Asset> getUserAssets(@PathVariable String userId,
			@PageableDefault(page = 0, size = 20) Pageable pageable) {

		Page<Asset> assets = this.assetMongoRepository.findByUserId(userId, pageable);

		for (Asset asset : assets) {
			this.rabbitTemplate.convertAndSend(MessagingConstants.USAGE_ROUTE,
					new UsageEvent("view asset", asset.toString()));
		}

		if (!assets.hasContent()) {
			logger.debug("Download assets failed for userId {}, no asset found.", userId);
		}

		return assets;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/browse")
	public List<String> browseFiles(Model model) {

		File rootFolder = new File(wabiSharePath);

		return Arrays.stream(rootFolder.listFiles()).map(f -> f.getName()).collect(Collectors.toList());
	}
}