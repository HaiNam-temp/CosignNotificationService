package com.cosign.NotificationService.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class UserNoticePreferenceId implements Serializable {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "notification_type")
    private String notificationType;
}
