package com.primebiz.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;

@FeignClient(name = "primebiz-user-service")
public interface UserClient {
    @GetMapping("/users/count")
    Map<String, Object> getUserCount();
}
