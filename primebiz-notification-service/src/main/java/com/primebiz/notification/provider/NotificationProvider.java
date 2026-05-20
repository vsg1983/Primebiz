package com.primebiz.notification.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationProvider {
    public void sendEmail(String to, String subject, String body) {
        log.info("[EMAIL] Sending to {}: {} - {}", to, subject, body);
    }

    public void sendSms(String phone, String body) {
        log.info("[SMS] Sending to {}: {}", phone, body);
    }

    public void sendWhatsApp(String phone, String body) {
        log.info("[WhatsApp] Sending to {}: {}", phone, body);
    }
}
