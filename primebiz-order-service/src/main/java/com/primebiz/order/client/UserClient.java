package com.primebiz.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "primebiz-user-service")
public interface UserClient {
    @GetMapping("/users/{userId}")
    Map<String, Object> getProfile(@PathVariable Long userId);
}
