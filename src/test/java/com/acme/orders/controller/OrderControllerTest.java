package com.acme.orders.controller;

import com.acme.orders.entity.Order;
import com.acme.orders.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnAllOrders() throws Exception {
        // given
        Order order1 = new Order("Product 1", 2, 19.99);
        order1.setId(1L);
        Order order2 = new Order("Product 2", 1, 29.99);
        order2.setId(2L);

        when(orderService.getAllOrders()).thenReturn(Arrays.asList(order1, order2));

        // when & then
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].productName", is("Product 1")))
                .andExpect(jsonPath("$[0].quantity", is(2)))
                .andExpect(jsonPath("$[0].unitPrice", is(19.99)))
                .andExpect(jsonPath("$[0].totalPrice", is(39.98)));
    }

    @Test
    public void shouldReturnOrderById() throws Exception {
        // given
        Order order = new Order("Product 1", 2, 19.99);
        order.setId(1L);

        when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));

        // when & then
        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.productName", is("Product 1")))
                .andExpect(jsonPath("$.totalPrice", is(39.98)));
    }

    @Test
    public void shouldReturn404WhenOrderNotFound() throws Exception {
        // given
        when(orderService.getOrderById(99L)).thenReturn(Optional.empty());

        // when & then
        mockMvc.perform(get("/api/orders/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateOrder() throws Exception {
        // given
        Order orderToCreate = new Order("New Product", 3, 9.99);
        Order createdOrder = new Order("New Product", 3, 9.99);
        createdOrder.setId(10L);

        when(orderService.createOrder(any(Order.class))).thenReturn(createdOrder);

        // when & then
        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.productName", is("New Product")))
                .andExpect(jsonPath("$.quantity", is(3)))
                .andExpect(jsonPath("$.unitPrice", is(9.99)))
                .andExpect(jsonPath("$.totalPrice", is(29.97)));
    }

    @Test
    public void shouldReturnValidationErrorsForInvalidOrder() throws Exception {
        // given
        Order invalidOrder = new Order("", 0, -5.0);

        // when & then
        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidOrder)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Validation Error")))
                .andExpect(jsonPath("$.validationErrors", hasSize(3)))
                .andExpect(jsonPath("$.validationErrors[*].field",
                        containsInAnyOrder("productName", "quantity", "unitPrice")))
                .andExpect(jsonPath("$.validationErrors[?(@.field=='productName')].message",
                        contains("Product name is required")))
                .andExpect(jsonPath("$.validationErrors[?(@.field=='quantity')].message",
                        contains("Quantity must be at least 1")))
                .andExpect(jsonPath("$.validationErrors[?(@.field=='unitPrice')].message",
                        contains("Unit price cannot be negative")));
    }

    @Test
    public void shouldReturnNotFoundForMissingOrder() throws Exception {
        // given
        when(orderService.getOrderById(any(Long.class))).thenReturn(Optional.empty());

        // when & then
        mockMvc.perform(get("/api/orders/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Not Found")))
                .andExpect(jsonPath("$.message", containsString("Order not found with id")));
    }

    @Test
    public void shouldReturnFormattedErrorForNotFound() throws Exception {
        // given
        when(orderService.getOrderById(any(Long.class))).thenReturn(Optional.empty());

        // when & then
        mockMvc.perform(get("/api/orders/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Order not found with id: 99")));
    }
}