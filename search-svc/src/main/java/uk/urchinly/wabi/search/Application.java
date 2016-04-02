/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.search;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import uk.urchinly.wabi.constants.MessagingConstants;
import uk.urchinly.wabi.entities.Asset;
import uk.urchinly.wabi.messages.AuditMessage;

@SpringBootApplication
@RestController
@RabbitListener(queues = MessagingConstants.NEW_ARTICLE_UPLOAD_ROUTE)
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Autowired
	private AssetRepository assetRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@RequestMapping("/")
	public RedirectView home() {
		return new RedirectView("/info");
	}

	@RequestMapping(path = "/users", method = RequestMethod.GET)
	public List<Asset> users(@RequestParam(value = "userId", defaultValue = "test") String userId) {
		return this.assetRepository.findByUserId(userId);
	}

	@RabbitHandler
	public void process(@Payload Asset asset) {
		Asset savedAsset = this.assetRepository.save(asset);
		this.rabbitTemplate.convertAndSend(MessagingConstants.AUDIT_ROUTE, new AuditMessage("success", asset));
		logger.debug("Added new asset {} to search index.", savedAsset.getId());
	}

}
