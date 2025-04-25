package com.example.ecommerce.service;


import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.exception.InvalidProductId;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.model.Orders;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.serviceimplementation.OrderServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Orders testOrder;
    private Product testProduct;
    private OrderItem testOrderItem;

    @BeforeEach
    void setUp(){
        testProduct = Product.builder()
                .id(1L)
                .name("Test Product")
                .price(19.99)
                .build();

        testOrderItem = OrderItem.builder()
                .id(1L)
                .product(testProduct)
                .quantity(2)
                .price(19.99)
                .build();

        testOrder= Orders.builder()
                .id(1L)
                .orderName("Test Order")
                .customerEmail("abc@gmail.com")
                .status(OrderStatus.PENDING)
                .orderItems(List.of(testOrderItem))
                .totalAmount(800.5)
                .build();
    }

    @Test
    void getAllOrdersShouldReturnAllOrders(){
        List<Orders> expectedOrder= Arrays.asList(testOrder,Orders.builder()
                .id(2L)
                .orderName("Test Order2")
                .customerEmail("xyz@gmail.com")
                .status(OrderStatus.PENDING)
                .orderItems(List.of(testOrderItem))
                .totalAmount(800.5)
                .build());

        List<Orders>actualOrders=orderService.getAllOrders();

        assertEquals(expectedOrder.size(), actualOrders.size());
        assertEquals(expectedOrder, actualOrders);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void createOrderShouldThrowExceptionWhenProductNotFound() {
        Orders orderToCreate = Orders.builder().customerEmail("customer@example.com").build();
        Product product = Product.builder().id(100L).build();
        OrderItem orderItem = OrderItem.builder().quantity(2).product(product).build();
        orderToCreate.setOrderItems(List.of(orderItem));

        when(productRepository.findById(100L)).thenReturn(Optional.empty());

        InvalidProductId exception = assertThrows(InvalidProductId.class, () -> {
            orderService.createOrder(orderToCreate);
        });

        assertEquals("Product with Id: 100 is not available in inventory", exception.getMessage());

        verify(productRepository, times(1)).findById(100L);
        verify(orderItemRepository, never()).save(any(OrderItem.class));
        verify(orderRepository, never()).save(any(Orders.class));
    }


    @Test
    void updateOrderStatus_ShouldUpdateStatusWhenOrderExists() {

        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        when(orderRepository.save(any(Orders.class))).thenReturn(testOrder);

        orderService.updateOrderStatus(1L, OrderStatus.SHIPPED);

        assertEquals(OrderStatus.SHIPPED, testOrder.getStatus());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(testOrder);
    }
}
