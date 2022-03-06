package com.lik.listener;

import cn.hutool.json.JSONUtil;
import com.lik.mq.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RocketMQTransactionListener
public class RoTransactionListener2 implements RocketMQLocalTransactionListener {

    /**
     * 消息预提交成功就会触发该方法的执行，用于完成本地事务
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.info("--------------------- executeLocalTransaction2 --------------------");
        log.info("arg: " + arg);
        log.info("消息：" + JSONUtil.toJsonStr(msg));
        String payload = new String((byte[]) msg.getPayload());
        log.info("自定义异常");
        int n = 10 / 0;
        log.info("payload：" + payload);
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    /**
     * executeLocalTransaction 返回 RocketMQLocalTransactionState.UNKNOWN 时执行,
     * executeLocalTransaction 方法出现异常也是 UNKNOWN
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info("--------------------- checkLocalTransaction2 ---------------------");
        log.info("消息：" + JSONUtil.toJsonStr(msg));
        log.info("msg: " + msg.toString());
        String payload = new String((byte[]) msg.getPayload());
        log.info("payload: " + payload);

        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
