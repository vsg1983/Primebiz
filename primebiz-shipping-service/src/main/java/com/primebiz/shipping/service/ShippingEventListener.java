package com.primebiz.shipping.service;

import com.primebiz.shipping.dto.PaymentCompletedEvent; // This will need to be created/imported
import com.primebiz.shipping.service.ShippingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingEventListener {

    private final ShippingService shippingService;
    private final com.primebiz.shipping.client.OrderClient orderClient; // To get address from user/order

    @Bean
    public Consumer<PaymentCompletedEvent> paymentCompleted() {
        return event -> {
            if ("SUCCESS".equalsIgnoreCase(event.getStatus())) {
                log.info("Payment successful for order {}. Triggering shipment...", event.getOrderId());
                
                // In a real system, we'd call Order/User service to get the address.
                // For this simulation, we'll use a dummy address or mock the lookup.
                String mockAddress = event.getOrderId() % 2 == 0 ? "Madurai, Tamil Nadu" : "Bangalore, Karnataka";
                
                shippingService.createShipment(event.getOrderId(), 1L, mockAddress);
                log.info("Shipment created for order {}", event.getOrderId());
            }
        };
    }
}
