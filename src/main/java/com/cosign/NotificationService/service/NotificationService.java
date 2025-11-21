package com.cosign.NotificationService.service;

import com.cosign.NotificationService.presetation.rest.NotificationController;
import com.cosign.NotificationService.presetation.rest.dto.NoticeRequestDto;

public interface NotificationService {
    void handleNotificationRequest(NoticeRequestDto noticeRequestDto);

}
