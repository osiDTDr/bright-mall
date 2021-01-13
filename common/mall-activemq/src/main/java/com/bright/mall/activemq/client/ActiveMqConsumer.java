package com.bright.mall.activemq.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.jms.annotation.JmsListener;

/**
 * activemq consumer
 *
 * @author zhengyuan
 * @since 2021/01/13
 */
@Component
public class ActiveMqConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ActiveMqConsumer.class);

    /**
     * queue模式的消费者
     *
     * @param message mq message
     */
    @JmsListener(destination = "${spring.activemq.queue-name}", containerFactory = "queueListener")
    public void readActiveQueue(String message) {
        if (logger.isInfoEnabled()) {
            logger.info("receive message from queue is {}", message);
        }
    }

    /**
     * topic模式的消费者
     *
     * @param message mq message
     */
    @JmsListener(destination = "${spring.activemq.topic-name}", containerFactory = "topicListener")
    public void readActiveTopic(String message) {
        if (logger.isInfoEnabled()) {
            logger.info("receive message from topic is {}", message);
        }
    }
}
