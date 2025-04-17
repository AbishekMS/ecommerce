package com.example.ecommerce.serviceimplementation;

import com.example.ecommerce.enums.ProductCategory;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired  
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductByID(Long id){
        return productRepository.findById(id);
    }

    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category.toUpperCase());
    }

    public List<Product> searchByName(String name) {
        return productRepository.findByName(name);
    }

    public Product saveProduct(Product product) {
        if (ValidateUtils.isValidString(product.getName())) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }

        ProductCategory category;
        try {
            category = ProductCategory.valueOf(product.getCategory().toString().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid product category: " + product.getCategory());
        }

        Product productToSave = Product.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(category)
                .build();

        return productRepository.save(productToSave);
    }


    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
