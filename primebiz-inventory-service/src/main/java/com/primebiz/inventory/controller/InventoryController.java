package com.primebiz.inventory.controller;

import com.primebiz.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{productId}")
    public ResponseEntity<Integer> getStock(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getStockLevel(productId));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateStock(@RequestBody Map<String, Object> request) {
        Long productId = Long.valueOf(request.get("productId").toString());
        Integer quantity = Integer.valueOf(request.get("quantity").toString());
        String reason = (String) request.get("reason");
        
        inventoryService.updateStock(productId, quantity, reason);
        return ResponseEntity.ok("Stock updated successfully");
    }

    @PostMapping("/deduct")
    public ResponseEntity<?> deductStock(@RequestBody Map<String, Object> request) {
        Long productId = Long.valueOf(request.get("productId").toString());
        Integer quantity = Integer.valueOf(request.get("quantity").toString());
        
        boolean success = inventoryService.deductStock(productId, quantity);
        if (success) {
            return ResponseEntity.ok("Stock deducted successfully");
        } else {
            return ResponseEntity.badRequest().body("Insufficient stock");
        }
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Long>> getLowStock(@RequestParam(defaultValue = "10") Integer threshold) {
        return ResponseEntity.ok(inventoryService.getLowStockProducts(threshold));
    }
}
