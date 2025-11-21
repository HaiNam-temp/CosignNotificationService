package com.cosign.NotificationService.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "scheduled_notifications")
@Data
public class ScheduledNotificationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "notification_type", nullable = false)
    private String notificationType;

    @Column(name = "channels", nullable = false)
    private String channels;

    @Column(name = "schedule_time", nullable = false)
    private Timestamp scheduleTime;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name = "priority", nullable = false)
    private Short priority;

    @Column(name = "status", nullable = false)
    private String status ;

    @Column(name = "retries")
    private Integer retries;

    @Column(name = "created_at")
    private Timestamp createdAt ;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt ;
}
