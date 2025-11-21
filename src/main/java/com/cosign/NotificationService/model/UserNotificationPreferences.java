package com.cosign.NotificationService.model;

import com.cosign.NotificationService.model.entities.UserNoticePreferenceId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserNotificationPreferences {

    private UserNoticePreferenceId id;

    private Boolean channelEmail ;

    private Boolean channelSms;

    private Boolean channelPush;

    private Boolean channelInapp;

    private Timestamp doNotDisturbStart;

    private Timestamp doNotDisturbEnd;

    private Integer dailyLimitPromotional;

    private Timestamp lastResetDate;
}