package com.codingwithchitra.codingwithchitra;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TelegramWebhookController {

    private final TelegramService telegramService;

    public TelegramWebhookController(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @PostMapping("/webhook")
    public void handleUpdate(@RequestBody Map<String, Object> update) {
        Map<String, Object> message = (Map<String, Object>) update.get("message");

        if (message != null && message.containsKey("contact")) {
            Map<String, Object> contact = (Map<String, Object>) message.get("contact");
            String phoneNumber = (String) contact.get("phone_number");
            String chatId = String.valueOf(((Map<String, Object>) message.get("chat")).get("id"));
            telegramService.sendMessage(chatId, "Thank you for sharing your phone number: " + phoneNumber);
        } else if (message != null && message.containsKey("text")) {
            String chatId = String.valueOf(((Map<String, Object>) message.get("chat")).get("id"));
            telegramService.requestPhoneNumber(chatId);
        }
    }
}
