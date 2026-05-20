package com.primebiz.payment.service;

import com.primebiz.payment.client.OrderClient;
import com.primebiz.payment.dto.*;
import com.primebiz.payment.model.*;
import com.primebiz.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;

    @Transactional
    public PaymentResponseDTO initiatePayment(PaymentRequestDTO request) {
        PaymentMethod method = PaymentMethod.valueOf(request.getMethod().toUpperCase());
        
        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .method(method)
                .status(PaymentStatus.PENDING)
                .timestamp(LocalDateTime.now())
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        if (method == PaymentMethod.COD) {
            completePayment(savedPayment.getId(), PaymentStatus.SUCCESS);
        }

        return PaymentResponseDTO.builder()
                .paymentId(savedPayment.getId())
                .status(savedPayment.getStatus().name())
                .transactionId(savedPayment.getTransactionId())
                .build();
    }

    @Transactional
    public void completePayment(Long paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(status);
        if (status == PaymentStatus.SUCCESS) {
            payment.setTransactionId(UUID.randomUUID().toString());
        }
        
        paymentRepository.save(payment);

        // Notify Order Service
        String orderStatus = (status == PaymentStatus.SUCCESS) ? "PAID" : "CANCELLED";
        orderClient.updateOrderStatus(payment.getOrderId(), Map.of("status", orderStatus));
    }

    public Payment getPayment(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}
