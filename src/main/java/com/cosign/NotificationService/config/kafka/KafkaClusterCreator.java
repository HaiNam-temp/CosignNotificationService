package com.cosign.NotificationService.config.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class KafkaClusterCreator {

    private final CosignKafkaProperties kafkaProperties;

    public <K, V> ProducerFactory<K, V> createProducerFactory() {
        KafkaProperties.Producer producer = kafkaProperties.getProducer();
        Map<String, Object> config = producer.buildProperties(null);

        if (kafkaProperties.getBootstrapServers() != null && !kafkaProperties.getBootstrapServers().isEmpty()) {
            config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, StringUtils.collectionToCommaDelimitedString(kafkaProperties.getBootstrapServers()));
        }
        if (kafkaProperties.getProperties() != null) {
            config.putAll(kafkaProperties.getProperties());
        }

        return new DefaultKafkaProducerFactory<>(config);
    }

    public <K, V> KafkaTemplate<K, V> createKafkaTemplate() {
        return new KafkaTemplate<>(createProducerFactory());
    }

    public ConsumerFactory<String, String> createConsumerFactory() {
        KafkaProperties.Consumer consumer = kafkaProperties.getConsumer();
        Map<String, Object> config = consumer.buildProperties(null);

        if (kafkaProperties.getBootstrapServers() != null && !kafkaProperties.getBootstrapServers().isEmpty()) {
            config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, StringUtils.collectionToCommaDelimitedString(kafkaProperties.getBootstrapServers()));
        }

        if (kafkaProperties.getProperties() != null) {
            config.putAll(kafkaProperties.getProperties());
        }

        return new DefaultKafkaConsumerFactory<>(config);
    }

    public ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createConsumerFactory());
        return factory;
    }

    public ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory(ContainerProperties.AckMode ackMode, int concurrency) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(concurrency);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("kafka-worker-");
        executor.initialize();

        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createConsumerFactory());
        factory.getContainerProperties().setAckMode(ackMode);
        factory.getContainerProperties().setListenerTaskExecutor(executor);
        return factory;
    }

    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        return new KafkaAdmin(configs);
    }

}

