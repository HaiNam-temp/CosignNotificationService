package com.cosign.NotificationService.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "notification_logs")
@Data
public class NotificationLogsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id",nullable = false)
    private String userId;

    @Column(name="notification_type",nullable = false)
    private String notificationType;

    @Column(name="message",columnDefinition = "TEXT")
    private String message;

    @Column(name="channel",nullable = false)
    private String channel;

    @Column(name="status",nullable = false)
    private String status;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name="error_message")
    private String errorMessage;

    @Column(name="retry_count")
    private Long retryCount;

    @Column(name="timestamp")
    private Long timestamp;

    @Column(name="sent_at")
    private  Long sentAt;

    @Column(name="update_at")
    private Long updatedAt;
}
