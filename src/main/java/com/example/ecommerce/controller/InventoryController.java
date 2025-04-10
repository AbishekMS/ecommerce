package com.example.ecommerce.controller;

import com.example.ecommerce.exception.InvalidProductId;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.InventoryService;
import com.example.ecommerce.model.Inventory;
import com.example.ecommerce.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory(){
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id){
        return inventoryService.getInventoryById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable Long id){
        return inventoryService.getInventoryByProductId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/lowstock")
    public ResponseEntity<List<Inventory>> getLowStockInventory(){
        return ResponseEntity.ok(inventoryService.getLowStockInventory());
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory){
        Long productId= inventory.getProduct().getId();
        Product product= productRepository.findById(productId).orElseThrow(()-> new InvalidProductId(productId));
        inventory.setProduct(product);
        return new ResponseEntity<>(inventoryService.saveInventory(inventory), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id,@RequestBody Inventory inventory){
        return  inventoryService.getInventoryById(id).map(e-> {
            inventory.setId(id);
            return ResponseEntity.ok(inventoryService.saveInventory(inventory));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/decreaseStock/{productId}/{quantity}")
    public ResponseEntity<String> deleteInventoryId(@PathVariable Long productId, @PathVariable int quantity){
        productRepository.findById(productId).orElseThrow(()-> new InvalidProductId(productId));

        boolean flag= inventoryService.decreaseStocksAtInventory(productId,quantity);
        if(flag) return ResponseEntity.ok("Stock decreased successfully");
        else return ResponseEntity.badRequest().body("Failed to decrease the stock, stock is insufficient");
    }
}
