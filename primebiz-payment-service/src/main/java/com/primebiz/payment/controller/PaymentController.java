package com.primebiz.payment.controller;

import com.primebiz.payment.dto.*;
import com.primebiz.payment.model.Payment;
import com.primebiz.payment.model.PaymentStatus;
import com.primebiz.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<PaymentResponseDTO> initiatePayment(@RequestBody PaymentRequestDTO request) {
        return ResponseEntity.ok(paymentService.initiatePayment(request));
    }

    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebhook(@RequestParam Long paymentId, @RequestParam String status) {
        PaymentStatus paymentStatus = PaymentStatus.valueOf(status.toUpperCase());
        paymentService.completePayment(paymentId, paymentStatus);
        return ResponseEntity.ok("Payment updated via webhook");
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> getPaymentStatus(@PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.getPayment(paymentId));
    }
}
