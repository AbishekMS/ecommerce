package com.example.ecommerce.Service;

import com.example.ecommerce.Repository.InventoryRepository;
import com.example.ecommerce.Repository.OrderItemRepository;
import com.example.ecommerce.Repository.OrderRepository;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.model.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private InventoryService inventoryService;


    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Orders> getOrdersByMail(String email) {
        return orderRepository.findByEmail(email);  // this won't generate derived query(generated with function name) because "email" won't match with customer_email field
    }

    public Optional<Orders> getOrdersById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Orders> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    @Transactional
    public Orders createOrder(Orders order) {
        order.setOrderName("Order- "+UUID.randomUUID().toString().substring(0,8).toUpperCase());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        Orders savedOrder= orderRepository.save(order);
        for(OrderItem item: order.getOrderItems()){
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }
        return savedOrder;

    }

    @Transactional
    public void updateOrderStatus(Long id, String status) {
        Optional<Orders> order=orderRepository.findById(id);
        order.ifPresent(eventOrder->{
            eventOrder.setStatus(status);
            orderRepository.save(eventOrder);
        });
    }

    public List<Orders> getOrdersByDateRange(LocalDateTime st, LocalDateTime end){
        return orderRepository.findByDateRange(st,end);
    }

    public List<OrderItem> getOrderItemByOrderId(Long id){
        return orderItemRepository.findByOrderId(id);
    }

    public List<OrderItem> getOrderItemByProductId(Long id){
        return orderItemRepository.findByProdctId(id);
    }

}
