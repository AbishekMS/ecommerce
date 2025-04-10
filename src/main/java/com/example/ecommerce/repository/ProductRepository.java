package com.example.ecommerce.repository;

import com.example.ecommerce.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "select * from product where category= ?1", nativeQuery = true)
    List<Product> findByCategory(String category);

    @Query(value = "select * from product where name like concat('%', ?1,'%')",nativeQuery = true)//use @Param("keyword") string name and change ?1 to :keyword
    List<Product> findByName(String name);
}
