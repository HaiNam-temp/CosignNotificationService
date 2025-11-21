package com.cosign.NotificationService.port.kafka;

public interface KafkaNotificationPublisher {
    public <T> void publish(T requestMessage , String topic);
}
