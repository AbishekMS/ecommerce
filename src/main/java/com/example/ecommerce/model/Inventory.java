package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;



@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
    private Integer quantity;

    @Column(name = "reorder_level")
    private Integer minimumStockLevel;
    @Column(name="location")
    private String storageLocation;

    public void setId(Long id) {
        this.id=id;
    }


}
