package com.github.oxaoo.springkafkaissue.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
public class KafkaPub {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaPub.class);

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
        ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(message);
        SendResult<String, String> sendResult;
        try {
            sendResult = future.get();
            LOG.info("Sent {} metadata: {}", data, sendResult.getRecordMetadata().toString());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public void setKafkaTemplate(final KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
