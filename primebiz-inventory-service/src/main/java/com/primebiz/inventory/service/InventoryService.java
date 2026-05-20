package com.primebiz.inventory.service;

import com.primebiz.inventory.model.Inventory;
import com.primebiz.inventory.model.StockLog;
import com.primebiz.inventory.repository.InventoryRepository;
import com.primebiz.inventory.repository.StockLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final StockLogRepository stockLogRepository;

    @Transactional
    public void updateStock(Long productId, Integer quantity, String reason) {
        Inventory inventory = inventoryRepository.findById(productId)
                .orElseGet(() -> Inventory.builder().productId(productId).quantity(0).build());
        
        inventory.setQuantity(inventory.getQuantity() + quantity);
        inventoryRepository.save(inventory);

        StockLog log = StockLog.builder()
                .productId(productId)
                .quantityChanged(quantity)
                .reason(reason)
                .timestamp(LocalDateTime.now())
                .build();
        stockLogRepository.save(log);
    }

    @Transactional
    public boolean deductStock(Long productId, Integer quantity) {
        Inventory inventory = inventoryRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found in inventory"));

        if (inventory.getQuantity() < quantity) {
            return false;
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);

        StockLog log = StockLog.builder()
                .productId(productId)
                .quantityChanged(-quantity)
                .reason("ORDER_DEDUCTION")
                .timestamp(LocalDateTime.now())
                .build();
        stockLogRepository.save(log);
        
        return true;
    }

    public Integer getStockLevel(Long productId) {
        return inventoryRepository.findById(productId)
                .map(Inventory::getQuantity)
                .orElse(0);
    }

    public List<Long> getLowStockProducts(Integer threshold) {
        return inventoryRepository.findAll().stream()
                .filter(i -> i.getQuantity() < threshold)
                .map(Inventory::getProductId)
                .collect(Collectors.toList());
    }
}
