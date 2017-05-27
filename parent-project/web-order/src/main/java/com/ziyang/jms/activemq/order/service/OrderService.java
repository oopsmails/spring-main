package com.ziyang.jms.activemq.order.service;

import java.util.Map;

import com.ziyang.jms.activemq.order.model.InventoryResponse;
import com.ziyang.jms.activemq.order.model.Order;

public interface OrderService {
	public void sendOrder(Order order);
	
	public void updateOrder(InventoryResponse response);
	
	public Map<String, Order> getAllOrders();
}
