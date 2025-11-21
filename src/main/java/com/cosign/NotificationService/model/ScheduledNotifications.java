package com.cosign.NotificationService.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ScheduledNotifications {

    private Long id;

    private String userId;

    private String notificationType;

    private String channels;

    private Timestamp scheduleTime;

    private Boolean isRead;

    private Short priority;

    private String status ;

    private Integer retries;

    private Timestamp createdAt ;

    private Timestamp updatedAt ;
}