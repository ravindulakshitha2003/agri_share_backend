package com.example.notification.messaging;


import com.example.notification.dto.NotificationEvent;
import com.example.notification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class NotificationEventConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;

    public NotificationEventConsumer(
            ObjectMapper objectMapper,
            NotificationService notificationService
    ) {
        this.objectMapper = objectMapper;
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "notification-events")
    public void consume(String message) throws Exception {

        NotificationEvent event = objectMapper.readValue(
                message,
                NotificationEvent.class
        );

        notificationService.processEvent(event);

        System.out.println(
                "Processed notification event: " + event.eventId()
        );
    }
}