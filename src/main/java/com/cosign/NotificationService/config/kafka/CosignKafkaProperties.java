package com.cosign.NotificationService.config.kafka;


import lombok.Data;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class CosignKafkaProperties {

    private List<String> bootstrapServers;
    private KafkaProperties.Producer producer = new KafkaProperties.Producer();
    private KafkaProperties.Consumer consumer = new KafkaProperties.Consumer();
    private Map<String, String> properties = new HashMap<>();
}

