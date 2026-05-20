package com.primebiz.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Map;

@FeignClient(name = "primebiz-order-service")
public interface OrderClient {
    @GetMapping("/orders/stats")
    Map<String, Object> getOrderStats();
}
