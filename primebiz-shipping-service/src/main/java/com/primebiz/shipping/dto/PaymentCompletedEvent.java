package com.primebiz.shipping.dto;

import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCompletedEvent implements Serializable {
    private Long paymentId;
    private Long orderId;
    private String status;
    private BigDecimal amount;
}
