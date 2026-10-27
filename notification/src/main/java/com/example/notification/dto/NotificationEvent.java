package com.example.notification.dto;



import java.time.Instant;

public record NotificationEvent(
        String eventId,
        String eventType,
        String recipientUsername,
        String title,
        String message,
        Instant createdAt
) {}