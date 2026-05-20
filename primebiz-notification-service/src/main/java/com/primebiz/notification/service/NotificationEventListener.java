package com.primebiz.notification.service;

import com.primebiz.notification.dto.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationEventListener {

    private final NotificationService notificationService;

    @Bean
    public Consumer<PaymentCompletedEvent> paymentCompleted() {
        return event -> {
            log.info("Notification service received payment event for order: {}", event.getOrderId());
            
            // For this simulation, we use a mock userId as events usually carry it.
            // In a real system, PaymentCompletedEvent would have the userId.
            Long mockUserId = 1L; 
            
            notificationService.sendPaymentConfirmation(mockUserId, event.getOrderId(), event.getStatus());
        };
    }
}
