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
    private String orderName;
    private LocalDateTime orderDate;
    private String customerName;
    private String customerEmail;
    private Double totalAmount;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}
