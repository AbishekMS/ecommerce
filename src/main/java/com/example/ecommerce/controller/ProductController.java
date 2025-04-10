package com.example.ecommerce.controller;

import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/getProductByID/{id}")
    public ResponseEntity<Product> getProductByID(@PathVariable Long id){
        Product product = productService.getProductByID(id).orElse(null);
        return (product != null) ? ResponseEntity.ok(product): ResponseEntity.notFound().build();
        // or return productService.getProductByID(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable String category){
        return ResponseEntity.ok(productService.findByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name){
        return ResponseEntity.ok(productService.searchByName(name));
    }
    //http://localhost:8081/products/search?name=Noodles

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Product>> createAtOnce(@RequestBody List<Product> products){
        return new ResponseEntity<>(productRepository.saveAll(products), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product){
        return productService.getProductByID(id).map(e -> {
            product.setId(id);
            return ResponseEntity.ok(productService.saveProduct(product));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if (productService.getProductByID(id).isPresent()) {
            productService.deleteProduct(id);
            return ResponseEntity.ok("ID: " + id + " deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID: " + id + " not found");
    }

}