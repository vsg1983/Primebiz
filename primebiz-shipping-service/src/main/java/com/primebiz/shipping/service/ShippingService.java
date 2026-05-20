package com.primebiz.shipping.service;

import com.primebiz.shipping.client.OrderClient;
import com.primebiz.shipping.dto.ShipmentResponseDTO;
import com.primebiz.shipping.model.*;
import com.primebiz.shipping.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShippingService {

    private final ShipmentRepository shipmentRepository;
    private final OrderClient orderClient;

    @Transactional
    public ShipmentResponseDTO createShipment(Long orderId, Long userId, String address) {
        // Decide carrier based on address
        Carrier carrier = address.toLowerCase().contains("madurai") ? Carrier.LOCAL : Carrier.SHIPROCKET;
        
        Shipment shipment = Shipment.builder()
                .orderId(orderId)
                .userId(userId)
                .shippingAddress(address)
                .carrier(carrier)
                .status(ShippingStatus.PREPARING)
                .trackingNumber(UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .estimatedDeliveryDate(LocalDateTime.now().plusDays(carrier == Carrier.LOCAL ? 1 : 5))
                .build();

        Shipment saved = shipmentRepository.save(shipment);
        
        // Initially just preparing, but we can simulate a ship-out
        return mapToDTO(saved);
    }

    @Transactional
    public void markAsShipped(Long shipmentId) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
        
        shipment.setStatus(ShippingStatus.SHIPPED);
        shipmentRepository.save(shipment);

        // Notify Order Service
        orderClient.updateOrderStatus(shipment.getOrderId(), Map.of("status", "SHIPPED"));
    }

    public ShipmentResponseDTO getShipmentByOrderId(Long orderId) {
        Shipment shipment = shipmentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("No shipment found for order " + orderId));
        return mapToDTO(shipment);
    }

    private ShipmentResponseDTO mapToDTO(Shipment s) {
        return ShipmentResponseDTO.builder()
                .shipmentId(s.getId())
                .trackingNumber(s.getTrackingNumber())
                .status(s.getStatus().name())
                .carrier(s.getCarrier().name())
                .estimatedDeliveryDate(s.getEstimatedDeliveryDate())
                .build();
    }
}
