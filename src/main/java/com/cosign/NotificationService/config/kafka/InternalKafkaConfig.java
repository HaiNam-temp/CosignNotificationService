package com.cosign.NotificationService.config.kafka;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;

@Data
@Configuration
public class InternalKafkaConfig {

    @Value("${kafka.internal.consumer.thread-size}")
    private Integer consumerThreadSize;

    @Bean
    @ConfigurationProperties(prefix = "kafka.internal")
    CosignKafkaProperties internalKafkaProperties() {
        return new CosignKafkaProperties();
    }

    @Bean
    KafkaClusterCreator kafkaClusterCreator() {
        return new KafkaClusterCreator(internalKafkaProperties());
    }

    @Bean
    KafkaTemplate<String, String> kafkaTemplate() {
        return kafkaClusterCreator().createKafkaTemplate();
    }

    @Bean
    KafkaAdmin kafkaAdmin() {
        return kafkaClusterCreator().kafkaAdmin();
    }

    @Bean
    @Primary
    ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory() {
        return kafkaClusterCreator().listenerContainerFactory(ContainerProperties.AckMode.RECORD, consumerThreadSize);
    }

}