package com.primebiz.order.service;

import com.primebiz.order.client.InventoryClient;
import com.primebiz.order.client.ProductClient;
import com.primebiz.order.client.UserClient;
import com.primebiz.order.dto.*;
import com.primebiz.order.model.*;
import com.primebiz.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final ProductClient productClient;
    private final UserClient userClient;

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        // 1. Verify User & Address
        Map<String, Object> profile = userClient.getProfile(request.getUserId());
        if (profile == null) throw new RuntimeException("User profile not found");

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        // 2. Process Items
        for (OrderItemDTO itemDto : request.getItems()) {
            // Verify Product & Price
            Map<String, Object> product = productClient.getProduct(itemDto.getProductId());
            if (product == null) throw new RuntimeException("Product not found: " + itemDto.getProductId());
            
            BigDecimal unitPrice = new BigDecimal(product.get("price").toString());
            
            // Check & Deduct Stock
            Integer stock = inventoryClient.getStock(itemDto.getProductId());
            if (stock == null || stock < itemDto.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + itemDto.getProductId());
            }

            Map<String, Object> deductRequest = Map.of(
                "productId", itemDto.getProductId(),
                "quantity", itemDto.getQuantity()
            );
            inventoryClient.deductStock(deductRequest);

            OrderItem orderItem = OrderItem.builder()
                    .productId(itemDto.getProductId())
                    .quantity(itemDto.getQuantity())
                    .unitPrice(unitPrice)
                    .build();
            orderItems.add(orderItem);
            
            totalAmount = totalAmount.add(unitPrice.multiply(BigDecimal.valueOf(itemDto.getQuantity())));
        }

        // 3. Persist Order
        Order order = Order.builder()
                .userId(request.getUserId())
                .orderDate(LocalDateTime.now())
                .totalAmount(totalAmount)
                .status(OrderStatus.PENDING)
                .shippingAddressId(request.getShippingAddressId())
                .items(orderItems)
                .build();

        // Set back-reference for items
        orderItems.forEach(item -> item.setOrder(order));

        Order savedOrder = orderRepository.save(order);

        return OrderResponseDTO.builder()
                .orderId(savedOrder.getId())
                .totalAmount(savedOrder.getTotalAmount())
                .status(savedOrder.getStatus().name())
                .orderDate(savedOrder.getOrderDate().toString())
                .build();
    }

    public OrderResponseDTO getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        return OrderResponseDTO.builder()
                .orderId(order.getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus().name())
                .orderDate(order.getOrderDate().toString())
                .build();
    }

    public List<OrderResponseDTO> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(o -> OrderResponseDTO.builder()
                        .orderId(o.getId())
                        .totalAmount(o.getTotalAmount())
                        .status(o.getStatus().name())
                        .orderDate(o.getOrderDate().toString())
                        .build())
                .collect(Collectors.toList());
    }
}
