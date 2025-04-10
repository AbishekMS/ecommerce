package com.example.ecommerce.repository;

import com.example.ecommerce.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query(value = "select * from inventory where product_id = ?1", nativeQuery = true)
    Optional<Inventory> findByProductId(Long id);

    @Query(value = "select * from inventory where quantity<=reorder_level",nativeQuery = true)
    List<Inventory> findLowStocks();

    @Modifying
    @Transactional
    @Query(value = "update inventory set quantity= quantity- ?2 where product_id= ?1 and quantity>= ?2",nativeQuery = true)
    int decreaseStocks(Long id, int quantity);
}
