/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.expose;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uk.urchinly.wabi.constants.MessagingConstants;
import uk.urchinly.wabi.entities.Asset;
import uk.urchinly.wabi.messages.UsageMessage;

@RestController
public class DownloadController {

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
	public List<Asset> getAsset(@PathVariable String assetId) {

		List<Asset> assets = this.assetMongoRepository.findByUserId(assetId);
		this.rabbitTemplate.convertAndSend(MessagingConstants.USAGE_ROUTE, new UsageMessage("download asset", assets.get(0)));
		
		return assets;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/assets/user/{userId}")
	public List<Asset> getUserAssets(@PathVariable String userId) {

		List<Asset> assets = this.assetMongoRepository.findByUserId(userId);

		return assets;
	}
}