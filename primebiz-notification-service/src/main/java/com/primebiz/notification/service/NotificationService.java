package com.primebiz.notification.service;

import com.primebiz.notification.client.UserClient;
import com.primebiz.notification.provider.NotificationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserClient userClient;
    private final NotificationProvider provider;

    public void sendPaymentConfirmation(Long userId, Long orderId, String status) {
        Map<String, Object> profile = userClient.getProfile(userId);
        String lang = profile != null && profile.containsKey("language") ? profile.get("language").toString() : "English";
        String phone = profile != null && profile.containsKey("phoneNumber") ? profile.get("phoneNumber").toString() : "unknown";
        String email = profile != null && profile.containsKey("email") ? profile.get("email").toString() : "unknown";

        String message = getMessageForStatus(status, lang, orderId);
        
        provider.sendEmail(email, "Order Update", message);
        provider.sendSms(phone, message);
        provider.sendWhatsApp(phone, message);
    }

    private String getMessageForStatus(String status, String lang, Long orderId) {
        if ("SUCCESS".equalsIgnoreCase(status)) {
            return "English".equalsIgnoreCase(lang) 
                ? "Payment successful for Order #" + orderId + ". Thank you for shopping with Primebiz!"
                : "ஆர்டர் #" + orderId + " க்கான கட்டணம் வெற்றிகரமாக செலுத்தப்பட்டது. பிரைம்பிஸில் ஷாப்பிங் செய்ததற்கு நன்றி!";
        } else {
            return "English".equalsIgnoreCase(lang)
                ? "Payment failed for Order #" + orderId + ". Please try again."
                : "ஆர்டர் #" + orderId + " க்கான கட்டணம் தோல்வியுற்றது. மீண்டும் முயற்சிக்கவும்.";
        }
    }
}
