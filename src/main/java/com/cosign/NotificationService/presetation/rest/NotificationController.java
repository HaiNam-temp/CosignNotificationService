package com.cosign.NotificationService.presetation.rest;

import com.cosign.NotificationService.common.ApiResponse;
import com.cosign.NotificationService.presetation.rest.dto.NoticeRequestDto;
import com.cosign.NotificationService.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/notices")
@Slf4j
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    @PostMapping("/send")
    public ResponseEntity<ApiResponse<?>> sendNotice(@RequestBody NoticeRequestDto request) {
        log.info("[NoticeController - sendNotice] Sending notice");
        notificationService.handleNotificationRequest(request);
        return ResponseEntity.ok(new ApiResponse<>("200", "Notice sent successfully", null));
    }

}
