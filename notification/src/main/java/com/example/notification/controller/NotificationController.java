package com.example.notification.controller;





import com.example.notification.entity.Notification;
import com.example.notification.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService
    ) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Notification> getNotifications(
            @RequestParam String username
    ) {
        return notificationService.getNotifications(username);
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable Long id,
            @RequestParam String username
    ) {
        boolean updated = notificationService.markAsRead(
                id,
                username
        );

        return updated
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    public record RegisterDeviceRequest(String username, String pushToken) {}

    @PostMapping("/devices")
    public ResponseEntity<Void> registerDevice(
            @RequestBody RegisterDeviceRequest request
    ) {
        if (request.username() == null
                || request.username().isBlank()
                || request.pushToken() == null
                || request.pushToken().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        notificationService.registerDevice(
                request.username(),
                request.pushToken()
        );

        return ResponseEntity.ok().build();
    }
}