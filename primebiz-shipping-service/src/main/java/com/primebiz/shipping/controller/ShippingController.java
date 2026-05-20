package com.primebiz.shipping.controller;

import com.primebiz.shipping.dto.ShipmentResponseDTO;
import com.primebiz.shipping.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipments")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingService shippingService;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ShipmentResponseDTO> getShipmentByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(shippingService.getShipmentByOrderId(orderId));
    }

    @PatchMapping("/{shipmentId}/ship")
    public ResponseEntity<?> markAsShipped(@PathVariable Long shipmentId) {
        shippingService.markAsShipped(shipmentId);
        return ResponseEntity.ok("Shipment marked as shipped");
    }
}
