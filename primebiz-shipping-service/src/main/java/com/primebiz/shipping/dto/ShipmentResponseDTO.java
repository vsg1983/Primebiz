package com.primebiz.shipping.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentResponseDTO {
    private Long shipmentId;
    private String trackingNumber;
    private String status;
    private String carrier;
    private LocalDateTime estimatedDeliveryDate;
}
