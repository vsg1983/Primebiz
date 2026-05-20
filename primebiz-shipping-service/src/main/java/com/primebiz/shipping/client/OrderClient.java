package com.primebiz.shipping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "primebiz-order-service")
public interface OrderClient {
    @PatchMapping("/orders/{orderId}/status")
    void updateOrderStatus(@PathVariable Long orderId, @RequestBody Map<String, String> statusUpdate);
}
