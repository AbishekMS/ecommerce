package com.example.ecommerce.serviceimplementation;

import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.exception.InvalidProductId;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.model.Orders;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private  OrderRepository orderRepository;
    private  OrderItemRepository orderItemRepository;
    private  ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

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
        double amt=0.0;
        order.setOrderName("Order- "+ UUID.randomUUID().toString().substring(0,8).toUpperCase());
        order.setOrderDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        order.setStatus(OrderStatus.PENDING);

        for(OrderItem item: order.getOrderItems()){
            Long productId=item.getProduct().getId();
            Product product= productRepository.findById(productId)
                                              .orElseThrow(()-> new InvalidProductId(productId));
           /* item.setPrice(product.getPrice());
            item.setProduct(product);
            item.setOrder(order);*/

            OrderItem buildItem= OrderItem.builder()
                    .product(product)
                    .price(product.getPrice())
                    .order(order)
                    .build();

            amt+= product.getPrice()* item.getQuantity();
            orderItemRepository.save(buildItem);
        }
        order.setTotalAmount(amt);
        return orderRepository.save(order);

    }

    @Transactional
    public void updateOrderStatus(Long id, OrderStatus status) {
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

    public void deleteByProductId(Long id) {
        orderRepository.deleteById(id);
    }


}
