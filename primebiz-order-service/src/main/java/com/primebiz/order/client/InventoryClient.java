package com.primebiz.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "primebiz-inventory-service")
public interface InventoryClient {
    @GetMapping("/inventory/{productId}")
    Integer getStock(@PathVariable Long productId);

    @PostMapping("/inventory/deduct")
    ResponseEntity<?> deductStock(@RequestBody Map<String, Object> request);
}
