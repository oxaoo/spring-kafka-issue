package com.github.oxaoo.springkafkaissue;


import com.github.oxaoo.springkafkaissue.service.AppService;
import com.github.oxaoo.springkafkaissue.service.KafkaSub;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AppServiceTest extends SpringKafkaIssueApplicationTests {

    private AppService appService;
    private KafkaSub kafkaSub;

    @Before
    public void setUp() {
        this.kafkaSub.getMessages().clear();
    }

    @Test
    public void errorAfterSucceededAttemptsTest() throws InterruptedException {
        this.appService.foo("test_1");
        this.appService.foo("test_2");
        try {
            this.appService.foo("test_bar");
        } catch (final IllegalArgumentException e) {
            assertEquals("Illegal message: test_bar", e.getMessage());
        }
        Thread.sleep(2000);
        final List<Object> msg = this.kafkaSub.getMessages();
        assertEquals(2, msg.size());
        assertEquals("\"test_1\"", msg.get(0));
        assertEquals("\"test_2\"", msg.get(1));
    }

    /**
     * Expects that "test_bar" message won't come to the Kafka topic, but... I see unexpected behaviour
     */
    @Test
    public void errorAtBeginningTest() throws InterruptedException {
        try {
            this.appService.foo("test_bar");
        } catch (final IllegalArgumentException e) {
            assertEquals("Illegal message: test_bar", e.getMessage());
        }
        this.appService.foo("test_1");

        Thread.sleep(2000);
        final List<Object> msg = this.kafkaSub.getMessages();
        assertEquals(1, msg.size());
        assertEquals("\"test_1\"", msg.get(0));
    }

    @Autowired
    public void setAppService(final AppService appService) {
        this.appService = appService;
    }

    @Autowired
    public void setKafkaSub(final KafkaSub kafkaSub) {
        this.kafkaSub = kafkaSub;
    }
}
