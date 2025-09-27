package com.acme.orders.service;

import com.acme.orders.entity.Order;
import com.acme.orders.error.ResourceNotFoundException;
import com.acme.orders.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        try {
            logger.info("Fetching all orders");
            return orderRepository.findAll();
        } catch (Exception e) {
            logger.error("Error fetching all orders", e);
            throw new RuntimeException("Failed to retrieve orders", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        try {
            logger.info("Fetching order with id: {}", id);
            return orderRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        } catch (ResourceNotFoundException e) {
            // Just log and rethrow ResourceNotFoundException
            logger.error("Order not found with id: {}", id);
            throw e;
        } catch (Exception e) {
            // Log and wrap other exceptions
            logger.error("Error fetching order with id: {}", id, e);
            throw new RuntimeException("Failed to retrieve order with id: " + id, e);
        }
    }

    @Override
    @Transactional
    public Order createOrder(Order order) {
        try {
            logger.info("Creating new order for product: {}", order.getProductName());
            // Ensure totalPrice is not explicitly set (it's derived)
            Order savedOrder = orderRepository.save(order);
            logger.info("Order created successfully with id: {}", savedOrder.getId());
            return savedOrder;
        } catch (Exception e) {
            logger.error("Error creating order for product: {}", order.getProductName(), e);
            throw new RuntimeException("Failed to create order", e);
        }
    }
}