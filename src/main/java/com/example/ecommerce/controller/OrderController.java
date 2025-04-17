package com.example.ecommerce.controller;

import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.model.Orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrdersById(@PathVariable Long id){
        return orderService.getOrdersById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/customer-email/{email}")
    public ResponseEntity<List<Orders>> getOrdersByCustomerEmail(@PathVariable String email){
        return ResponseEntity.ok(orderService.getOrdersByMail(email));
    }

    @GetMapping("status/{status}")
    public ResponseEntity<List<Orders>> getOrdersByStatus(@PathVariable String status){
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }

    @PostMapping
    public ResponseEntity<Orders> createOrder(@RequestBody Orders order){
        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}/status/{status}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long id, @PathVariable String status){
       try {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            orderService.updateOrderStatus(id, orderStatus);
            return ResponseEntity.ok("Status Updated Successfully");
        } catch(IllegalArgumentException ex){
           return ResponseEntity.badRequest().body("Invalid status value: "+status);
       }

    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Orders>> getOrdersByDateRange(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime start,
                                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end){
        if(start==null) start= LocalDateTime.now();
        return ResponseEntity.ok(orderService.getOrdersByDateRange(start,end));
    }


}
