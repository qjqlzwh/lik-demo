package com.lik;

import cn.hutool.core.util.IdUtil;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 普通消息
     */
    @Test
    public void demo1() {
        for (int i = 0; i < 10; i++) {
            rocketMQTemplate.syncSend("topic-A", "消息  " + i + "  " + IdUtil.getSnowflake(1, 1).nextId());
        }
    }

    /**
     * 顺序消息
     */
    @Test
    public void demo2() {
        for (int i = 0; i < 10; i++) {
            String key = String.valueOf(i % rocketMQTemplate.getProducer().getDefaultTopicQueueNums());
            String msg = "订单  " + i + "  " + IdUtil.getSnowflake(1, 1).nextId();
            rocketMQTemplate.syncSendOrderly("topic-C", msg, key);
            msg = "发货单  " + i + "  " + IdUtil.getSnowflake(1, 1).nextId();
            rocketMQTemplate.syncSendOrderly("topic-C", msg, key);
        }
    }
}
