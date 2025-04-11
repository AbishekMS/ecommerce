// File: com/example/ecommerce/service/ProductService.java
package com.example.ecommerce.service;

import com.example.ecommerce.repository.ProductRepository;

import com.example.ecommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

public interface ProductService {
     List<Product> getProducts();

     Optional<Product> getProductByID(Long id);

     List<Product> findByCategory(String category);

     List<Product> searchByName(String name);

     Product saveProduct(Product product);

     void deleteProduct(Long id);
}