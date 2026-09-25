package com.placementhub.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NotificationResponse {

    private Long notificationId;

    private String message;

    private boolean isRead;

    private LocalDateTime createdAt;
}