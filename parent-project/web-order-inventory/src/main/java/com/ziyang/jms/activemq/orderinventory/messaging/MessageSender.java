package com.ziyang.jms.activemq.orderinventory.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.ziyang.jms.activemq.order.model.InventoryResponse;

@Component
public class MessageSender {
	static final Logger LOG = LoggerFactory.getLogger(MessageSender.class);

	@Autowired
	JmsTemplate jmsTemplate;

	public void sendMessage(final InventoryResponse inventoryResponse) {

		jmsTemplate.send(new MessageCreator(){
				@Override
				public Message createMessage(Session session) throws JMSException{
					ObjectMessage objectMessage = session.createObjectMessage(inventoryResponse);
					LOG.info("Application (web-order-inventory) : sending inventoryResponse : {}", inventoryResponse);
					return objectMessage;
				}
			});
	}

}
