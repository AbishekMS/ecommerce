package com.example.ecommerce.Service;

import com.example.ecommerce.Repository.InventoryRepository;
import com.example.ecommerce.model.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

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
        return  inventoryRepository.findLowStocks();
    }

    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public boolean decreaseStocksAtInventory(Long id, int quantity) {
        int cnt= inventoryRepository.decreaseStocks(id,quantity);
        return cnt>0;

    }
}
