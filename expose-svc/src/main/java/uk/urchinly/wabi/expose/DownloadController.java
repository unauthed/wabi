/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.expose;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uk.urchinly.wabi.constants.MessagingConstants;
import uk.urchinly.wabi.entities.WabiAsset;
import uk.urchinly.wabi.events.UsageEvent;

@RestController
public class DownloadController {

	private static final Logger logger = LoggerFactory.getLogger(DownloadController.class);

	@Value("${app.share.path}/vault")
	private String wabiSharePath;

	@Autowired
	private AssetRepository assetMongoRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@RequestMapping(method = RequestMethod.GET, value = "/assets")
	public List<Asset> getAssets() {

		List<Asset> assets = this.assetMongoRepository.findAll();

		return assets;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/assets/{assetId}")
	public List<WabiAsset> getAsset(@PathVariable String assetId) {

		List<WabiAsset> assets = this.assetMongoRepository.findByUserId(assetId);
		this.rabbitTemplate.convertAndSend(MessagingConstants.USAGE_ROUTE,
				new UsageEvent("download asset", assets.get(0)));

		return assets;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/assets/user/{userId}")
	public List<WabiAsset> getUserAssets(@PathVariable String userId) {

		List<WabiAsset> assets = this.assetMongoRepository.findByUserId(userId);

		return assets;
	}
}