package com.example.notification.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class ExpoPushService {

    private final RestClient restClient;

    public ExpoPushService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://exp.host/--/api/v2")
                .build();
    }

    public void send(
            String pushToken,
            String title,
            String message,
            Long notificationId
    ) {
        Map<String, Object> payload = Map.of(
                "to", pushToken,
                "title", title,
                "body", message,
                "sound", "default",
                "data", Map.of(
                        "notificationId", notificationId
                )
        );

        restClient.post()
                .uri("/send")
                .body(payload)
                .retrieve()
                .toBodilessEntity();
    }
}