package com.bright.mall.activemq.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 * activemq producer
 *
 * @author zhengyuan
 * @since 2021/01/13
 */
@Component
public class ActiveMqProducer {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    /**
     * send to mq queue
     *
     * @param message message
     */
    public void sendQueue(String message) {
        this.sendMessage(this.queue, message);
    }

    /**
     * send to mq topic
     *
     * @param message message
     */
    public void sendTopic(String message) {
        this.sendMessage(this.topic, message);
    }

    /**
     * send message to mq server
     *
     * @param destination 发送到的队列
     * @param message     待发送的消息
     */
    private void sendMessage(Destination destination, final String message) {
        jmsMessagingTemplate.convertAndSend(destination, message);
    }
}
