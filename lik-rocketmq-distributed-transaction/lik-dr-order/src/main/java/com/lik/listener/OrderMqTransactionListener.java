package com.lik.listener;

import cn.hutool.json.JSONUtil;
import com.lik.constant.MqTopic;
import com.lik.entity.order.Order;
import com.lik.enums.OrderState;
import com.lik.mq.MqMessage;
import com.lik.resp.R;
import com.lik.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RocketMQ事务消息，提交本地事务
 */
@Slf4j
@Component
@RocketMQTransactionListener
public class OrderMqTransactionListener implements RocketMQLocalTransactionListener {

    @Autowired
    private IOrderService orderService;

    /**
     * 消息预提交成功就会触发该方法的执行，用于完成本地事务
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.info("--------------------- executeLocalTransaction --------------------");
        String payload = new String((byte[]) msg.getPayload());
        log.info("payload：" + payload);
        Order order = JSONUtil.toBean(payload, Order.class);
        try {
            // 扣减库存
            orderService.reductionInventory(order);

            // 扣减余额
            orderService.reductionAmount(order);

            // 更新订单为正式订单
            order.setState(OrderState.SUBMIT.getVal());
            boolean success = orderService.updateById(order);
            Assert.isTrue(success, "更新订单状态失败！");
            // 。。。

        } catch (Exception e) {
            // 推送MQ, 回滚余额、库存
            return RocketMQLocalTransactionState.COMMIT;
        }

        // 取消消息
        return RocketMQLocalTransactionState.ROLLBACK;
    }

    /**
     * executeLocalTransaction 返回 RocketMQLocalTransactionState.UNKNOWN 时执行,
     * executeLocalTransaction 方法出现异常也是 UNKNOWN
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info("--------------------- checkLocalTransaction ---------------------");
        String payload = new String((byte[]) msg.getPayload());
        log.info("payload：" + payload);
        Order order = JSONUtil.toBean(payload, Order.class);
        Order order2 = orderService.getById(order.getId());
        if (order2.getState() == OrderState.SUBMIT.getVal()) {
            // 订单已提交，不需要推送Mq进行回滚
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        return RocketMQLocalTransactionState.UNKNOWN;
    }
}
