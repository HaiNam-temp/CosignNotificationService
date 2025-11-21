package com.cosign.NotificationService.presetation.rest.dto;

import com.cosign.NotificationService.constants.Channel;
import lombok.Data;

@Data
public class NoticeRequestDto {
        private String userId;
        private String email;
        private String phoneNumber;
        private String notificationType;
        private Channel channel;
        private String url;
        private String fileName;
        private EmailChannelDto emailChannel;
        private SmsChannelDto smsChannel;
        private InAppChannelDto inAppChannel;
}
