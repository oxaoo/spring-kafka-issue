package com.github.oxaoo.springkafkaissue.service;

import com.github.oxaoo.springkafkaissue.model.Message;
import com.github.oxaoo.springkafkaissue.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppService {

    private KafkaPub kafkaPub;
    private MessageRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void foo(final String msg) {
        this.repository.save(new Message(msg));
        this.kafkaPub.send(msg);
        //reproduce the exception
        //some logic the consequence of which is the exception
        if (msg.contains("bar")) {
            throw new IllegalArgumentException("Illegal message: " + msg);
        }
    }

    @Autowired
    public void setKafkaPub(final KafkaPub kafkaPub) {
        this.kafkaPub = kafkaPub;
    }

    @Autowired
    public void setRepository(final MessageRepository repository) {
        this.repository = repository;
    }
}
