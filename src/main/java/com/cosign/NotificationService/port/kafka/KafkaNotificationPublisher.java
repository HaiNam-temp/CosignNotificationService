package com.cosign.NotificationService.port.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaNotificationPublisher {
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    public <T> void publish(T requestMessage , String topic) {
        try {
            String message = objectMapper.writeValueAsString(requestMessage);
            kafkaTemplate.send(topic, message);
        } catch (Exception e) {
            log.error("Failed to publish notification message", e);
        }
    }
}
