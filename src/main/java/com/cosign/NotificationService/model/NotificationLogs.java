package com.cosign.NotificationService.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class NotificationLogs {

    private Long id;

    private String userId;

    private String notificationType;

    private String message;

    private String channel;

    private String status;

    private Boolean isRead;

    private String errorMessage;

    private Long retryCount;

    private Long timestamp;

    private  Long sentAt;

    private Long updatedAt;
}
