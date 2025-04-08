package com.example.ecommerce.Repository;

import com.example.ecommerce.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query(value = "select * from orders where customer_email=?1", nativeQuery = true)
    List<Orders> findByEmail(String email);

    List<Orders> findByStatus(String status);
}
