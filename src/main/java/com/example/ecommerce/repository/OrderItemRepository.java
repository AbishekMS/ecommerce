package com.example.ecommerce.repository;

import com.example.ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query(value = "select * from order_item where order_id =:id",nativeQuery = true)
    List<OrderItem> findByOrderId(Long id);

    @Query(value = "select * from order_item where product_id= :id", nativeQuery = true)
    List<OrderItem> findByProdctId(Long id);
}
