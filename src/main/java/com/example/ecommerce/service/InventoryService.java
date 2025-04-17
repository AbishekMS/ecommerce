package com.example.ecommerce.service;
import com.example.ecommerce.model.Inventory;
import java.util.List;
import java.util.Optional;

public interface InventoryService {
   List<Inventory> getAllInventory();

   Optional<Inventory> getInventoryById(Long id);

   Optional<Inventory> getInventoryByProductId(Long id);

   List<Inventory> getLowStockInventory();

   Inventory saveInventory(Inventory inventory);

    boolean decreaseStocksAtInventory(Long id, int quantity);
}
