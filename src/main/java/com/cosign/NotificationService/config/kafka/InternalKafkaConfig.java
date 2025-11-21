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

/**
 * Class này là "TRƯỞNG PHÒNG THIẾT LẬP" cho cụm Kafka nội bộ (Internal).
 * 
 * TÁC DỤNG:
 * 1. Đọc cấu hình từ file application.properties (những dòng bắt đầu bằng kafka.internal).
 * 2. Khởi tạo các CÔNG CỤ THỰC TẾ (Beans) như máy gửi tin (Template), máy nhận tin (Listener).
 * 3. Đưa các công cụ này vào kho của Spring để các class khác (Service/Controller) có thể lấy ra dùng.
 * 
 * Nếu không có class này, ứng dụng sẽ không biết cách kết nối tới Kafka và không có công cụ để gửi/nhận tin.
 */
@Data
@Configuration
public class InternalKafkaConfig {

    // Lấy giá trị số luồng từ file application.properties (key: kafka.internal.consumer.thread-size)
    @Value("${kafka.internal.consumer.thread-size}")
    private Integer consumerThreadSize;

    /**
     * Bước 1: HỨNG DỮ LIỆU CẤU HÌNH.
     * Tạo ra một object CosignKafkaProperties rỗng, sau đó Spring tự động đổ dữ liệu 
     * từ các dòng "kafka.internal..." trong file properties vào object này.
     */
    @Bean
    @ConfigurationProperties(prefix = "kafka.internal")
    CosignKafkaProperties internalKafkaProperties() {
        return new CosignKafkaProperties();
    }

    // tạo đối tưởng khởi tạo cụm Kafka
    @Bean
    KafkaClusterCreator kafkaClusterCreator() {
        return new KafkaClusterCreator(internalKafkaProperties());
    }

    // khởi tạo đối tượng Producer
    @Bean
    KafkaTemplate<String, String> kafkaTemplate() {
        return kafkaClusterCreator().createKafkaTemplate();
    }

    // khởi tạo đối tượng KafkaAdmin
    @Bean
    KafkaAdmin kafkaAdmin() {
        return kafkaClusterCreator().kafkaAdmin();
    }

    // khởi tạo đối tượng Listener Container Factory với chế độ xác nhận tin theo từng bản ghi
    @Bean
    @Primary
    ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory() {
        return kafkaClusterCreator().listenerContainerFactory(ContainerProperties.AckMode.RECORD, consumerThreadSize);
    }

}