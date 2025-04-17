package com.example.ecommerce.serviceimplementation;

import com.example.ecommerce.model.Inventory;
import com.example.ecommerce.repository.InventoryRepository;
import com.example.ecommerce.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {
    private InventoryRepository inventoryRepository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository)
    {
        this.inventoryRepository=inventoryRepository;
    }

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
        if(inventory.getProduct()==null || inventory.getProduct().getId()==null) throw new IllegalArgumentException("Product is required for inventory");
        return inventoryRepository.save(inventory);
    }

    public boolean decreaseStocksAtInventory(Long id, int quantity) {
        int cnt= inventoryRepository.decreaseStocks(id,quantity);
        return cnt>0;
    }
}
