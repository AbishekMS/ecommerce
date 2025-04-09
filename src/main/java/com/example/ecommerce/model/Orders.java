package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderName;  // this orderNumber
    private LocalDateTime orderDate;
    private String customerName;
    private String customerEmail;
    private Double totalAmount;
    private String status;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem>  getOrderItems() {
        return orderItems;
    }
}
