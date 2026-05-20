package com.primebiz.payment.dto;

import lombok.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCompletedEvent implements Serializable {
    private Long paymentId;
    private Long orderId;
    private String status;
    private java.math.BigDecimal amount;
}
