package com.example.ecommerce.Repository;

import com.example.ecommerce.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query(value = "select * from orders where customer_email=?1 order by order_date desc", nativeQuery = true)
    List<Orders> findByEmail(String email);

    @Query(value = "select * from orders where status= ?1", nativeQuery = true)
    List<Orders> findByStatus(String status);

    @Query(value = "select * from orders where order_date between :start and :end", nativeQuery = true)
    List<Orders> findByDateRange(@Param("start") LocalDateTime st, @Param("end") LocalDateTime end);
}
