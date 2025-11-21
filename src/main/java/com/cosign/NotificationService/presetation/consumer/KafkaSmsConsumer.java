package com.cosign.NotificationService.presetation.consumer;

import com.cosign.NotificationService.common.Topics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaSmsConsumer {
    @KafkaListener(
            topics = Topics.NOTICE_SMS_TOPIC,
            groupId = "notification-service",
            containerFactory = "listenerContainerFactory"
    )
    public void handlerSmsNotification(String message) {
        log.info("[KafkaInAppConsumer - handlerSmsNotification] Consumed SMS notification message: {}", message);
        // Here you would add the logic to process the in-app notification
    }
}
