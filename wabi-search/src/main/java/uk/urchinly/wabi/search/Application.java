/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.search;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import uk.urchinly.wabi.constants.MessagingConstants;
import uk.urchinly.wabi.events.AssetEvent;
import uk.urchinly.wabi.events.AuditEvent;

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

	@RabbitHandler
	public void process(@Payload() AssetEvent assetEvent) {
		Asset newAsset = new Asset();
		BeanUtils.copyProperties(assetEvent, newAsset);

		Asset savedAsset = this.assetRepository.save(newAsset);

		this.rabbitTemplate.convertAndSend(MessagingConstants.AUDIT_ROUTE,
				new AuditEvent("success", savedAsset).toString());

		logger.debug("Added new asset {} to search index.", savedAsset.getId());
	}

	@RabbitHandler
	public void process(@Payload() String assetEvent) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Asset newAsset = objectMapper.readValue(assetEvent, Asset.class);

		Asset savedAsset = this.assetRepository.save(newAsset);

		this.rabbitTemplate.convertAndSend(MessagingConstants.AUDIT_ROUTE,
				new AuditEvent("success", savedAsset).toString());

		logger.debug("Added new asset {} to search index.", savedAsset.getId());
	}
}
