package com.acme.orders.config;

import com.acme.orders.entity.Order;
import com.acme.orders.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeDatabase(OrderRepository orderRepository) {
        return args -> {
            Order order1 = new Order("iPhone 15", 2, 999.99);
            Order order2 = new Order("MacBook Pro", 1, 2499.99);
            Order order3 = new Order("AirPods Pro", 3, 249.99);
            Order order4 = new Order("iPad Air", 1, 599.99);
            Order order5 = new Order("Apple Watch", 2, 399.99);

            orderRepository.save(order1);
            orderRepository.save(order2);
            orderRepository.save(order3);
            orderRepository.save(order4);
            orderRepository.save(order5);

            System.out.println("Database initialized with " + orderRepository.count() + " orders");
        };
    }
}