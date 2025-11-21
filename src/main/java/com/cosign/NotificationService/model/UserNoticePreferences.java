package com.cosign.NotificationService.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "user_notice_preferences")
public class UserNoticePreferences {

    @EmbeddedId
    private UserNoticePreferenceId id;

    @Column(name = "channel_email")
    private Boolean channelEmail ;

    @Column(name = "channel_sms")
    private Boolean channelSms;

    @Column(name = "channel_push")
    private Boolean channelPush;

    @Column(name = "channel_inapp")
    private Boolean channelInapp;

    @Column(name = "do_not_disturb_start")
    private Timestamp doNotDisturbStart;

    @Column(name = "do_not_disturb_end")
    private Timestamp doNotDisturbEnd;

    @Column(name = "daily_limit_promotional")
    private Integer dailyLimitPromotional;

    @Column(name = "last_reset_date")
    private Timestamp lastResetDate;
}
