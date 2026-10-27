package com.example.notification.entity;


import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(
        name = "device_tokens",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_device_token",
                columnNames = "expo_push_token"
        )
)
public class DeviceToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "expo_push_token", nullable = false, length = 512)
    private String expoPushToken;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected DeviceToken() {}

    public DeviceToken(String username, String expoPushToken) {
        this.username = username;
        this.expoPushToken = expoPushToken;
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getExpoPushToken() { return expoPushToken; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void update(String username) {
        this.username = username;
        this.updatedAt = Instant.now();
    }
}