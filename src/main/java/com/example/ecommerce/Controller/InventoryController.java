package com.example.ecommerce.Controller;

import com.example.ecommerce.Service.InventoryService;
import com.example.ecommerce.model.Inventory;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    @Autowired
    private final InventoryService inventoryService;

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
        return new ResponseEntity<>(inventoryService.saveInventory(inventory), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id,@RequestBody Inventory inventory){
        return  inventoryService.getInventoryById(id).map(e-> {
            inventory.setId(id);
            return ResponseEntity.ok(inventoryService.saveInventory(inventory));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/decreaseStock/{id}/{quantity}")
    public ResponseEntity<String> deleteInventoryId(@PathVariable Long id, @PathVariable int quantity){
        boolean flag= inventoryService.decreaseStocksAtInventory(id,quantity);
        if(flag) return ResponseEntity.ok("Stock decreased successfully");
        else return ResponseEntity.badRequest().body("Failed to decrease the stock, either stock is insufficient or product not found");
    }




}
