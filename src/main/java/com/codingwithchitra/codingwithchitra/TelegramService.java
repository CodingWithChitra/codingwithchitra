package com.codingwithchitra.codingwithchitra;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TelegramService {

    private final String BOT_TOKEN = "7286829275:AAHqqGfzbhZaDImVecFmy23fnwFNFHhJrfI";
    private final String TELEGRAM_API_URL = "https://core.telegram.org/bots/api" + BOT_TOKEN;
    private final RestTemplate restTemplate;

    public TelegramService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String sendMessage(String chatId, String message) {
        String url = TELEGRAM_API_URL + "/sendMessage?chat_id=" + chatId + "&text=" + message;
        return restTemplate.getForObject(url, String.class);
    }

    public String requestPhoneNumber(String chatId) {
        String url = TELEGRAM_API_URL + "/sendMessage";

        Map<String, Object> keyboardButton = new HashMap<>();
        keyboardButton.put("text", "Share your phone number");
        keyboardButton.put("request_contact", true);

        Map<String, Object> replyKeyboard = new HashMap<>();
        replyKeyboard.put("keyboard", new Object[][]{{keyboardButton}});
        replyKeyboard.put("one_time_keyboard", true);
        replyKeyboard.put("resize_keyboard", true);

        Map<String, Object> messageData = new HashMap<>();
        messageData.put("chat_id", chatId);
        messageData.put("text", "Please share your phone number:");
        messageData.put("reply_markup", replyKeyboard);

        return restTemplate.postForObject(url, messageData, String.class);
    }
}
