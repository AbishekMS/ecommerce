package com.example.ecommerce.Controller;

import com.example.ecommerce.Service.OrderService;
import com.example.ecommerce.model.Orders;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    public  OrderService orderService;

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
        orderService.updateOrderStatus(id,status);
        return ResponseEntity.ok("Status Updated Successfully");

    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Orders>> getOrdersByDateRange(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime start,
                                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end){
        if(start==null) start= LocalDateTime.now();
        return ResponseEntity.ok(orderService.getOrdersByDateRange(start,end));
    }
/* cant delete parent row
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable Long id){
        if(orderService.getOrdersById(id).isPresent()){
            orderService.deleteByProductId(id);
            return ResponseEntity.ok("Order:"+ id +" deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
    }*/

}
