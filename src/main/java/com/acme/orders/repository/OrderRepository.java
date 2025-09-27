package com.acme.orders.repository;

import com.acme.orders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // The basic CRUD operations are automatically provided by Spring Data JPA
    // You can add custom query methods here if needed
}