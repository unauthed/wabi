/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.search;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import uk.urchinly.wabi.constants.MessagingConstants;

@Profile("prod")
@Component
public class AmqpQueues {

	@Autowired
	public AmqpQueues(AmqpAdmin amqpAdmin) {

		amqpAdmin.declareQueue(new Queue(MessagingConstants.NEW_ARTICLE_UPLOAD_ROUTE));
		amqpAdmin.declareQueue(new Queue(MessagingConstants.AUDIT_ROUTE));
	}

}