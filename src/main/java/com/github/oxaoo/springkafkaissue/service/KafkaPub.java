package com.github.oxaoo.springkafkaissue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;

@Component
public class KafkaPub {
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic}")
    private String kafkaTopic;

    private Map<String, Object> headers;

    @PostConstruct
    public void init() {
        this.headers = Collections.singletonMap(KafkaHeaders.TOPIC, this.kafkaTopic);
    }

    public void send(final String data) {
        final GenericMessage<String> message = new GenericMessage<>(data, this.headers);
        this.kafkaTemplate.send(message);
    }

    @Autowired
    public void setKafkaTemplate(final KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
