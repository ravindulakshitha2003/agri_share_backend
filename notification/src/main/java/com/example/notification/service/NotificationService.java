package com.example.notification.service;





import com.example.notification.dto.NotificationEvent;
import com.example.notification.entity.DeviceToken;
import com.example.notification.entity.Notification;
import com.example.notification.repository.DeviceTokenRepository;
import com.example.notification.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final DeviceTokenRepository deviceTokenRepository;
    private final ExpoPushService expoPushService;

    public NotificationService(
            NotificationRepository notificationRepository, DeviceTokenRepository deviceTokenRepository,

            ExpoPushService expoPushService
    ) {
        this.notificationRepository = notificationRepository;
        this.deviceTokenRepository = deviceTokenRepository;

        this.expoPushService = expoPushService;
    }

    @Transactional
    public void processEvent(NotificationEvent event) {

        if (notificationRepository
                .existsByEventIdAndRecipientUsername(
                        event.eventId(),
                        event.recipientUsername())) {
            return;
        }

        Notification notification = new Notification(
                event.eventId(),
                event.recipientUsername(),
                event.title(),
                event.message(),
                event.eventType(),
                event.createdAt() != null
                        ? event.createdAt()
                        : Instant.now()
        );

        notification = notificationRepository.save(notification);

        List<DeviceToken> devices =
                deviceTokenRepository.findByUsername(
                        event.recipientUsername()
                );

        // Push delivery is attempted for each registered device.
        for (DeviceToken device : devices) {
            try {
                expoPushService.send(
                        device.getExpoPushToken(),
                        notification.getTitle(),
                        notification.getMessage(),
                        notification.getId()
                );
            } catch (Exception ex) {
                // Keep the in-app notification even if push fails.
                System.err.println(
                        "Push delivery failed for notification "
                                + notification.getId()
                                + ": " + ex.getMessage()
                );
            }
        }
    }

    public List<Notification> getNotifications(String username) {
        return notificationRepository
                .findByRecipientUsernameOrderByCreatedAtDesc(username);
    }

    @Transactional
    public boolean markAsRead(Long id, String username) {
        return notificationRepository
                .findByIdAndRecipientUsername(id, username)
                .map(notification -> {
                    notification.setRead(true);
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public void registerDevice(
            String username,
            String pushToken
    ) {
        DeviceToken device = deviceTokenRepository
                .findByExpoPushToken(pushToken)
                .orElse(null);

        if (device == null) {
            deviceTokenRepository.save(
                    new DeviceToken(username, pushToken)
            );
        } else {
            device.update(username);
        }
    }
}