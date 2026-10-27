package com.example.notification.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(
        name = "notifications",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_notification_event_recipient",
                columnNames = {"event_id", "recipient_username"}
        )
)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private String eventId;

    @Column(name = "recipient_username", nullable = false)
    private String recipientUsername;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String message;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "is_read", nullable = false)
    private boolean read = false;

    protected Notification() {}

    public Notification(
            String eventId,
            String recipientUsername,
            String title,
            String message,
            String eventType,
            Instant createdAt
    ) {
        this.eventId = eventId;
        this.recipientUsername = recipientUsername;
        this.title = title;
        this.message = message;
        this.eventType = eventType;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getEventId() { return eventId; }
    public String getRecipientUsername() { return recipientUsername; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getEventType() { return eventType; }
    public Instant getCreatedAt() { return createdAt; }
    public boolean isRead() { return read; }

    public void setRead(boolean read) {
        this.read = read;
    }
}