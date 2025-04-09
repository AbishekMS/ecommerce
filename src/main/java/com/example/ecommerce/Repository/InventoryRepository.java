package com.example.ecommerce.Repository;

import com.example.ecommerce.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query(value = "select * from inventory where product_id = ?1", nativeQuery = true)
    Optional<Inventory> findByProductId(Long id);

    @Query("select * from inventory where quantity<=reorder_level")
    List<Inventory> findLowStocks();

    @Transactional
    @Query("update inventory set quantity= quantity- ?2 where product_id= ?1 and quantity>= ?2")
    int decreaseStocks(Long id, int quantity);
}
