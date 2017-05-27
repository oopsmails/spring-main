package com.ziyang.jms.activemq.orderinventory.service;

import com.ziyang.jms.activemq.order.model.Order;

public interface OrderInventoryService {

	public void processOrder(Order order);
	
}
