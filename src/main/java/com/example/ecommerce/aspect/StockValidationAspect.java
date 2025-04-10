package com.example.ecommerce.aspect;


import com.example.ecommerce.exception.InsufficientStockException;
import com.example.ecommerce.exception.InvalidProductId;
import com.example.ecommerce.model.Inventory;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.model.Orders;
import com.example.ecommerce.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class StockValidationAspect {
    private final InventoryService inventoryService;

    @Before("execution(* com.example.ecommerce.service.OrderService.createOrder(..)) && args(order)")
    public void validateStock(Orders order){
        for(OrderItem item: order.getOrderItems()){
            Long productId= item.getProduct().getId();
            Optional<Inventory> inventory= inventoryService.getInventoryByProductId(productId);
            if(inventory.isEmpty()){
                throw new InvalidProductId(productId);
            }
            if(inventory.get().getQuantity()< item.getQuantity()){
                throw new InsufficientStockException("Insufficient stock for product "+item.getProduct().getName()+". Available: "+ inventory.get().getQuantity()+", Requested: "+ item.getQuantity());
            }
        }
    }
}
