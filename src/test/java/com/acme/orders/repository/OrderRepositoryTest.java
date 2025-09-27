package com.acme.orders.repository;

import com.acme.orders.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void shouldSaveOrder() {
        // given
        Order order = new Order("Test Product", 2, 19.99);

        // when
        Order savedOrder = orderRepository.save(order);

        // then
        assertThat(savedOrder.getId()).isNotNull();
        assertThat(savedOrder.getProductName()).isEqualTo("Test Product");
        assertThat(savedOrder.getQuantity()).isEqualTo(2);
        assertThat(savedOrder.getUnitPrice()).isEqualTo(19.99);
        assertThat(savedOrder.getTotalPrice()).isEqualTo(39.98);
    }

    @Test
    public void shouldFindOrderById() {
        // given
        Order order = new Order("Test Product", 2, 19.99);
        Order persistedOrder = entityManager.persist(order);
        entityManager.flush();

        // when
        Optional<Order> foundOrder = orderRepository.findById(persistedOrder.getId());

        // then
        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getProductName()).isEqualTo("Test Product");
    }

    @Test
    public void shouldFindAllOrders() {
        // given
        Order order1 = new Order("Product 1", 2, 19.99);
        Order order2 = new Order("Product 2", 1, 29.99);
        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.flush();

        // when
        List<Order> orders = orderRepository.findAll();

        // then
        assertThat(orders).hasSize(2);
    }
}