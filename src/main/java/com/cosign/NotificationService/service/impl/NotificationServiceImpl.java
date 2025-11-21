package com.cosign.NotificationService.service.impl;

import com.cosign.NotificationService.common.Topics;
import com.cosign.NotificationService.port.kafka.KafkaNotificationPublisherImpl;
import com.cosign.NotificationService.presetation.rest.dto.NoticeRequestDto;
import com.cosign.NotificationService.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl  implements NotificationService {
        private final KafkaNotificationPublisherImpl kafkaNotificationPublisherImpl;
        @Override
        public void handleNotificationRequest(NoticeRequestDto noticeRequestDto) {
            log.info("Handling notification request: {}", noticeRequestDto);
            switch (noticeRequestDto.getChannel()) {
                case EMAIL:
                    log.info("Processing email notification");
                    kafkaNotificationPublisherImpl.publish(noticeRequestDto, Topics.NOTICE_EMAIL_TOPIC);
                    break;
                case IN_APP:
                    log.info("Processing in-app notification");
                    kafkaNotificationPublisherImpl.publish(noticeRequestDto, Topics.NOTICE_INAPP_TOPIC);
                    break;
                case SMS:
                    log.info("Processing SMS notification");
                    kafkaNotificationPublisherImpl.publish(noticeRequestDto, Topics.NOTICE_SMS_TOPIC);
                    break;
                default:
                    log.info("Unknown notification type: {}", noticeRequestDto.getChannel());
            }
        }
}
