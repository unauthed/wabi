/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.expose;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.urchinly.wabi.constants.MessagingConstants;

@Component
public class AmqpQueues {

	@Autowired
	public AmqpQueues(AmqpAdmin amqpAdmin) {

		amqpAdmin.declareQueue(new Queue(MessagingConstants.USAGE_ROUTE));
	}

}