package com.acme.orders.service;

import com.acme.orders.entity.Order;
import com.acme.orders.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order testOrder;

    @BeforeEach
    public void setUp() {
        testOrder = new Order("Test Product", 2, 19.99);
        testOrder.setId(1L);
    }

    @Test
    public void shouldReturnAllOrders() {
        // given
        Order order2 = new Order("Product 2", 1, 29.99);
        order2.setId(2L);
        List<Order> expectedOrders = Arrays.asList(testOrder, order2);
        when(orderRepository.findAll()).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.getAllOrders();

        assertThat(actualOrders).isEqualTo(expectedOrders);
        assertThat(actualOrders).hasSize(2);
    }

    @Test
    public void shouldReturnOrderById() {

        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        Optional<Order> foundOrder = orderService.getOrderById(1L);

        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getId()).isEqualTo(1L);
        assertThat(foundOrder.get().getProductName()).isEqualTo("Test Product");
    }

    @Test
    public void shouldReturnEmptyWhenOrderNotFound() {

        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Order> foundOrder = orderService.getOrderById(99L);

        assertThat(foundOrder).isEmpty();
    }

    @Test
    public void shouldCreateOrder() {

        Order orderToCreate = new Order("New Product", 3, 9.99);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            savedOrder.setId(10L);
            return savedOrder;
        });

        Order createdOrder = orderService.createOrder(orderToCreate);

        assertThat(createdOrder.getId()).isEqualTo(10L);
        assertThat(createdOrder.getProductName()).isEqualTo("New Product");
        assertThat(createdOrder.getQuantity()).isEqualTo(3);
        assertThat(createdOrder.getUnitPrice()).isEqualTo(9.99);
        assertThat(createdOrder.getTotalPrice()).isEqualTo(29.97);
    }
}