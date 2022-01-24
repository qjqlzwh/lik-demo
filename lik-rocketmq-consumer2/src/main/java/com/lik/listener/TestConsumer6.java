package com.lik.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "consumer-group-3", topic = "topic-B", messageModel = MessageModel.BROADCASTING)
public class TestConsumer6 implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("消费者6: " + message);
    }
}
