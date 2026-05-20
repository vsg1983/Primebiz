package com.primebiz.admin.service;

import com.primebiz.admin.client.*;
import com.primebiz.admin.dto.AdminDashboardStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final OrderClient orderClient;
    private final InventoryClient inventoryClient;
    private final UserClient userClient;

    public AdminDashboardStats getGlobalStats() {
        Map<String, Object> orderStats = orderClient.getOrderStats();
        Map<String, Object> userStats = userClient.getUserCount();
        List<Map<String, Object>> lowStock = inventoryClient.getLowStockProducts();

        return AdminDashboardStats.builder()
                .totalOrders(Long.valueOf(orderStats.getOrDefault("totalOrders", 0).toString()))
                .totalRevenue(Double.valueOf(orderStats.getOrDefault("totalRevenue", 0.0).toString()))
                .totalUsers(Long.valueOf(userStats.getOrDefault("count", 0).toString()))
                .lowStockAlerts(lowStock)
                .shippingSummary(Map.of("PENDING", 5L, "SHIPPED", 12L, "DELIVERED", 45L)) // Mocked summary
                .build();
    }
}
