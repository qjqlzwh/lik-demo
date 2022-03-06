package com.lik;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.lik.mq.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQLocalRequestCallback;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 普通消息
     */
    @Test
    public void demo0() {
        rocketMQTemplate.syncSend("topic-A", "消息 " + IdUtil.getSnowflake(1, 1).nextId());
    }

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

    @Test
    public void demo3() throws IOException {
        String msg = "消息 " + IdUtil.getSnowflake(1, 1).nextId();
        log.info(msg);
        String result = "";
        rocketMQTemplate.sendAndReceive("topic-D", msg, new RocketMQLocalRequestCallback<String>() {

            @Override
            public void onSuccess(String message) {
                log.info("操作成功");
//                result = message;
                log.info(message.toString());
            }

            @Override
            public void onException(Throwable e) {
                log.info("操作失败");
                log.error(e.toString());
            }
        });

        log.info("666666666");

        System.in.read();
    }

    @Test
    public void demo4() throws IOException {
        String msg = "消息 " + IdUtil.getSnowflake(1, 1).nextId();
        log.info(msg);
        Message<String> message = MessageBuilder.withPayload(msg).build();
        String uuid = IdUtil.fastSimpleUUID();
        log.info("uuid: " + uuid);

        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction("topic-C", message, uuid);

        sendResult.get

        log.info("sendResult: " + JSONUtil.toJsonStr(sendResult));
        log.info("sendResult: " + sendResult.toString());

        log.info(msg + " end ");

        System.in.read();
    }

    @Test
    public void demo5() throws IOException {
        String msg = "消息 " + IdUtil.getSnowflake(1, 1).nextId();
        String uuid = IdUtil.fastSimpleUUID();
        MqMessage mqMessage = new MqMessage(uuid, msg);
        log.info("uuid: " + uuid);
        String payload = JSONUtil.toJsonStr(mqMessage);
        log.info(payload);
        Message<String> message = MessageBuilder.withPayload(payload).build();
//        message.getHeaders().put("iuid", uuid);

        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction("topic-C", message, uuid);

        log.info(sendResult.toString());

        log.info(msg + " end ");

        System.in.read();
    }
}
