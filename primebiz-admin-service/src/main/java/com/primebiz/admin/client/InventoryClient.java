package com.primebiz.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Map;

@FeignClient(name = "primebiz-inventory-service")
public interface InventoryClient {
    @GetMapping("/inventory/low-stock")
    List<Map<String, Object>> getLowStockProducts();
}
