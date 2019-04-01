package com.github.oxaoo.springkafkaissue.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaSub {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaSub.class);

    private final List<Object> messages = new ArrayList<>();

    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group}")
    public void listen(final ConsumerRecord data) {
        LOG.info("Got data from kafka {}", data.toString());
        this.messages.add(data.value());
    }

    public List<Object> getMessages() {
        return messages;
    }
}
