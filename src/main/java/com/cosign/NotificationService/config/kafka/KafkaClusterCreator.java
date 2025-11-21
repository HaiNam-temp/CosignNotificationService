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

/**
 * Class này là "Công xưởng" (Factory).
 * Nhiệm vụ: Lấy thông tin cấu hình từ CosignKafkaProperties và lắp ráp thành các công cụ thực tế để sử dụng.
 */
@RequiredArgsConstructor
public class KafkaClusterCreator {

    private final CosignKafkaProperties kafkaProperties;

    /**
     * Tạo ra "Nhà máy sản xuất Producer".
     * Để gửi tin nhắn, thư viện Kafka cần một nơi quản lý kết nối và cấu hình.
     * Hàm này nạp địa chỉ server, nạp cấu hình serializer vào để chuẩn bị.
     */
    public <K, V> ProducerFactory<K, V> createProducerFactory() {
        KafkaProperties.Producer producer = kafkaProperties.getProducer();
        Map<String, Object> config = producer.buildProperties(null);

        // Nạp địa chỉ server Kafka vào cấu hình
        if (kafkaProperties.getBootstrapServers() != null && !kafkaProperties.getBootstrapServers().isEmpty()) {
            config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, StringUtils.collectionToCommaDelimitedString(kafkaProperties.getBootstrapServers()));
        }
        // Nạp các cấu hình khác
        if (kafkaProperties.getProperties() != null) {
            config.putAll(kafkaProperties.getProperties());
        }

        return new DefaultKafkaProducerFactory<>(config);
    }

    // Tạo ra công cụ gửi tin nhắn (KafkaTemplate)
    public <K, V> KafkaTemplate<K, V> createKafkaTemplate() {
        return new KafkaTemplate<>(createProducerFactory());
    }

    // Tạo ra "Nhà máy sản xuất Consumer".
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

     // Tạo môi trường chạy cho các hàm @KafkaListener (cơ bản).
    public ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createConsumerFactory());
        return factory;
    }


    public ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory(ContainerProperties.AckMode ackMode, int concurrency) {
        // Tạo một hồ chứa các luồng (Thread Pool) để quản lý nhân viên
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // Luôn duy trì ít nhất 5 nhân viên
        executor.setMaxPoolSize(concurrency); // Tối đa số nhân viên bằng tham số truyền vào
        executor.setQueueCapacity(50); // Nếu nhân viên bận hết, xếp hàng tối đa 50 việc
        executor.setThreadNamePrefix("kafka-worker-"); // Đặt tên cho nhân viên để dễ theo dõi log
        executor.initialize();

        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createConsumerFactory());
        
        // Cài đặt chế độ báo cáo (Ack)
        factory.getContainerProperties().setAckMode(ackMode);
        
        // Gán đội ngũ nhân viên (executor) vào để làm việc
        factory.getContainerProperties().setListenerTaskExecutor(executor);
        return factory;
    }

    // Tạo công cụ quản lý cụm Kafka (KafkaAdmin)
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        return new KafkaAdmin(configs);
    }

}

