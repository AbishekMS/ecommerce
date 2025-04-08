package com.example.ecommerce.Service;

import com.example.ecommerce.Repository.InventoryRepository;
import com.example.ecommerce.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class InventoryService {
    @Autowired
    public InventoryRepository inventoryRepository;

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getInventoryById(Long id) {
        return inventoryRepository.findById(id);
    }

    public Optional<Inventory> getInventoryByProductId(Long id) {
        return inventoryRepository.findByProductId(id);
    }

    public List<Inventory> getLowStockInventory() {
        return (List<Inventory>) inventoryRepository.findLowStocks();
    }

    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public boolean decreaseStocksAtInventory(Long id, int quantity) {
        int cnt= inventoryRepository.decreaseStocks(id,quantity);
        return cnt>0;

    }
}
