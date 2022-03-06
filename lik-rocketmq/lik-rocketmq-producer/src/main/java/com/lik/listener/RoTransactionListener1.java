package com.lik.listener;

import cn.hutool.json.JSONUtil;
import com.lik.mq.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
//@RocketMQTransactionListener
public class RoTransactionListener1 implements RocketMQLocalTransactionListener {

    Map<String, RocketMQLocalTransactionState> globalState = new ConcurrentHashMap<>();

    /**
     * 消息预提交成功就会触发该方法的执行，用于完成本地事务
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.info("--------------------- executeLocalTransaction --------------------");
        log.info("arg: " + arg.toString());
        log.info("消息：" + JSONUtil.toJsonStr(msg));
        String payload = new String((byte[]) msg.getPayload());
        log.info("payload：" + payload);
        MqMessage mqMessage = JSONUtil.toBean(payload, MqMessage.class);
        globalState.put(mqMessage.getUid(), RocketMQLocalTransactionState.UNKNOWN);

        log.info("11111111111111111111111");
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info("--------------------- checkLocalTransaction ---------------------");
        log.info("消息：" + JSONUtil.toJsonStr(msg));
        log.info("msg: " + msg.toString());
        String payload = new String((byte[]) msg.getPayload());
        log.info("payload: " + payload);

        return RocketMQLocalTransactionState.ROLLBACK;
//        MqMessage mqMessage = JSONUtil.toBean(payload, MqMessage.class);
//
//        log.info(JSONUtil.toJsonStr(mqMessage));
//        log.info(JSONUtil.toJsonStr(globalState));
//
//        if (globalState.get(mqMessage.getUid()) == null) {
//            return RocketMQLocalTransactionState.ROLLBACK;
//        }
//        return globalState.get(mqMessage.getUid());
    }
}
