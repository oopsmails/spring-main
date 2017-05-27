package com.ziyang.jms.activemq.order.service;

import java.util.Map;

import com.ziyang.jms.activemq.order.model.Order;

public interface OrderRepository {

	public void putOrder(Order order);
	
	public Order getOrder(String orderId);
	
	public Map<String, Order> getAllOrders();
}
