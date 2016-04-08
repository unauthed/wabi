/**
 * Wabi-Sabi DAM solution
 * Open source Digital Asset Management platform of great simplicity and beauty.
 * Copyright (C) 2016 Urchinly <wabi-sabi@urchinly.uk>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RabbitListener(queues = MessagingConstants.ASSET_INSERT_IMAGE_ROUTE)
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Autowired
	private AssetRepository assetRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@CrossOrigin
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
