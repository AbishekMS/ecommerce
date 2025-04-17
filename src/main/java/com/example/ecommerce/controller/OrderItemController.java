package com.example.ecommerce.controller;

import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/order-items")
public class OrderItemController {
    private OrderService orderService;

    @Autowired
    public OrderItemController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getOrderItemByOrderId(@PathVariable Long orderId){
        return ResponseEntity.ok(orderService.getOrderItemByOrderId(orderId));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<OrderItem>> getOrderItemByProductId(@PathVariable Long productId){
        return ResponseEntity.ok(orderService.getOrderItemByProductId(productId));
    }
}
