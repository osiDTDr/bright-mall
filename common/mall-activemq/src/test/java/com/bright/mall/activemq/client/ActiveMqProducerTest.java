package com.bright.mall.activemq.client;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ActiveMqProducerTest {
    private static final Logger logger = LoggerFactory.getLogger(ActiveMqProducerTest.class);

    @Autowired
    private ActiveMqProducer activeMqProducer;

    @Test
    public void testTopic() {
        activeMqProducer.sendTopic("topic test message");
        try {
            // thread sleep for waiting activemq consumer receive message
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            logger.error("thread interrupt error ", e);
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void testQueue() {
        activeMqProducer.sendQueue("queue test message");
        try {
            // thread sleep for waiting activemq consumer receive message
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            logger.error("thread interrupt error ", e);
            Thread.currentThread().interrupt();
        }
    }
}
