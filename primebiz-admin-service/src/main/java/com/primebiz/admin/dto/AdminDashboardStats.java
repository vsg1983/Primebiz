package com.primebiz.admin.dto;

import lombok.*;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardStats {
    private Long totalOrders;
    private Double totalRevenue;
    private Long totalUsers;
    private List<Map<String, Object>> lowStockAlerts;
    private Map<String, Long> shippingSummary;
}
