package com.cosign.NotificationService.presetation.consumer;

import com.cosign.NotificationService.common.Topics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaInAppConsumer {
    @KafkaListener(
            topics = Topics.NOTICE_INAPP_TOPIC,
            groupId = "notification-service",
            containerFactory = "listenerContainerFactory"
    )
    public void handlerInAppNotification(String message) {
        log.info("[KafkaInAppConsumer - handlerInAppNotification] Consumed in-app notification message: {}", message);
        // Here you would add the logic to process the in-app notification
    }
}
