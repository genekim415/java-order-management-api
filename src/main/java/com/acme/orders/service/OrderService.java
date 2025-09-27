package com.acme.orders.service;

import com.acme.orders.entity.Order;
import com.acme.orders.error.ResourceNotFoundException;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    Order getOrderById(Long id) throws ResourceNotFoundException;

    Order createOrder(Order order);
}