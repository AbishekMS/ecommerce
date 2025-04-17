package com.example.ecommerce.service;

import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.model.Orders;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface OrderService {

     List<Orders> getAllOrders();

    List<Orders> getOrdersByMail(String email);

    Optional<Orders> getOrdersById(Long id);

    List<Orders> getOrdersByStatus(String status);

    Orders createOrder(Orders order);

    void updateOrderStatus(Long id, OrderStatus status);

    List<Orders> getOrdersByDateRange(LocalDateTime st, LocalDateTime end);

    List<OrderItem> getOrderItemByOrderId(Long id);

    List<OrderItem> getOrderItemByProductId(Long id);

    void deleteByProductId(Long id);
}
