package com.github.oxaoo.springkafkaissue.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaBrokerAddress;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        final Map<String, Object> configuration = new HashMap<>();
        configuration.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaBrokerAddress);
        configuration.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "tx.");
        final DefaultKafkaProducerFactory<String, String> kafkaProducerFactory
                = new DefaultKafkaProducerFactory<>(configuration, new StringSerializer(), new StringSerializer());
        kafkaProducerFactory.setTransactionIdPrefix("tx.");
        return kafkaProducerFactory;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        final KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(this.producerFactory());
        kafkaTemplate.setMessageConverter(new StringJsonMessageConverter());
        return kafkaTemplate;
    }
}
