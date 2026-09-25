package com.placementhub.backend.service;

import com.placementhub.backend.dto.NotificationResponse;
import com.placementhub.backend.entity.Notification;
import com.placementhub.backend.entity.User;
import com.placementhub.backend.repository.NotificationRepository;
import com.placementhub.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(
            NotificationRepository notificationRepository,
            UserRepository userRepository
    ) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public void createNotification(
            Long userId,
            String message
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Notification notification = new Notification();

        notification.setUser(user);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }


    public List<NotificationResponse> getMyNotifications(
            String email
    ) {

        User user = getUserByEmail(email);

        return notificationRepository
                .findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    public List<NotificationResponse> getUnreadNotifications(
            String email
    ) {

        User user = getUserByEmail(email);

        return notificationRepository
                .findByUserIdAndIsReadFalseOrderByCreatedAtDesc(
                        user.getId()
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public long getUnreadCount(
            String email
    ) {

        User user = getUserByEmail(email);

        return notificationRepository
                .countByUserIdAndIsReadFalse(user.getId());
    }

    public void markAsRead(
            Long notificationId,
            String email
    ) {

        User user = getUserByEmail(email);

        Notification notification =
                notificationRepository
                        .findByIdAndUserId(
                                notificationId,
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification not found"
                                ));

        notification.setRead(true);

        notificationRepository.save(notification);
    }


    public void markAllAsRead(
            String email
    ) {

        User user = getUserByEmail(email);

        List<Notification> notifications =
                notificationRepository
                        .findByUserIdAndIsReadFalseOrderByCreatedAtDesc(
                                user.getId()
                        );

        for (Notification notification : notifications) {
            notification.setRead(true);
        }

        notificationRepository.saveAll(notifications);
    }

    public void deleteNotification(
            Long notificationId,
            String email
    ) {

        User user = getUserByEmail(email);

        Notification notification =
                notificationRepository
                        .findByIdAndUserId(
                                notificationId,
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification not found"
                                ));

        notificationRepository.delete(notification);
    }

    private User getUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    private NotificationResponse convertToResponse(
            Notification notification
    ) {

        return new NotificationResponse(
                notification.getId(),
                notification.getMessage(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }
}