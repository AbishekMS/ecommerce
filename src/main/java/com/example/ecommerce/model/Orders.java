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
    private String orderDate;
    private String customerName;
    private String customerEmail;
    private Double totalAmount;
    private String status;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    /*
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }*/


}
