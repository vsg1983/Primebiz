package com.primebiz.payment.service;

import com.primebiz.payment.dto.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventProducer {
    private final StreamBridge streamBridge;

    public void sendPaymentEvent(PaymentCompletedEvent event) {
        streamBridge.send("paymentCompleted-out-0", event);
    }
}
