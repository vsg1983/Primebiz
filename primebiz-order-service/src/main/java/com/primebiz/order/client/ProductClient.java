package com.primebiz.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Map;

@FeignClient(name = "primebiz-product-service")
public interface ProductClient {
    @GetMapping("/products/{id}")
    Map<String, Object> getProduct(@PathVariable Long id);
}
