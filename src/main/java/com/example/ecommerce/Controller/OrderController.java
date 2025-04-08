package com.example.ecommerce.Controller;

import com.example.ecommerce.Service.OrderService;
import com.example.ecommerce.model.Orders;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    public OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id")
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
        orderService.updateOrderStatus(id,status);
        return ResponseEntity.ok("Status Updated Successfully");

    }


}
