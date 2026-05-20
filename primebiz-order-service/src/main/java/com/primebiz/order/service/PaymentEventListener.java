package com.primebiz.order.service;

import com.primebiz.order.dto.PaymentCompletedEvent;
import com.primebiz.order.model.Order;
import com.primebiz.order.model.OrderStatus;
import com.primebiz.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventListener {

    private final OrderRepository orderRepository;

    @Bean
    public Consumer<PaymentCompletedEvent> paymentCompleted() {
        return event -> {
            log.info("Received payment completion event for order: {}", event.getOrderId());
            
            Order order = orderRepository.findById(event.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found: " + event.getOrderId()));
            
            if ("SUCCESS".equalsIgnoreCase(event.getStatus())) {
                order.setStatus(OrderStatus.PAID);
            } else {
                order.setStatus(OrderStatus.CANCELLED);
            }
            
            orderRepository.save(order);
            log.info("Updated order {} status to {}", event.getOrderId(), order.getStatus());
        };
    }
}
