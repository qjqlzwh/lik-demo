package com.lik.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.lik.constant.MqTopic;
import com.lik.dto.product.ProductDTO;
import com.lik.entity.order.Order;
import com.lik.entity.product.Product;
import com.lik.enums.OrderState;
import com.lik.feign.product.ProductFeign;
import com.lik.mq.MqMessage;
import com.lik.resp.R;
import com.lik.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Resource(name = "sendRocketMqMsgExecutor")
    private ExecutorService executorService;

    /**
     * 方案一
     * @param order
     * @return
     */
    @RequestMapping("/saveOrder")
    public R saveOrder(@RequestBody Order order) {
        // 数据校验、生成预订单
        orderService.createPreOrder(order);
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
            // 推送MQ, 回滚余额、回滚库存、取消订单...
            executorService.submit(() -> {
                rocketMQTemplate.syncSend(MqTopic.ORDER_ROLLBACK, JSONUtil.toJsonStr(order));
            });
            return R.error(e.getMessage());
        }
        return R.ok();
    }

    /**
     * 方案二：RocketMQ事务消息
     * @param order
     * @return
     */
    @RequestMapping("/saveOrder2")
    public R saveOrder2(@RequestBody Order order) throws Exception {
        log.info("saveOrder2 start");

        // 数据校验、生成预订单
        orderService.createPreOrder(order);

        String payload = JSONUtil.toJsonStr(order);
        Message<String> message = MessageBuilder.withPayload(payload).build();
//        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction(MqTopic.ORDER_ROLLBACK, message, null);

        Future<TransactionSendResult> future = executorService.submit(() -> {
            TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction(MqTopic.ORDER_ROLLBACK, message, null);
            return sendResult;
        });
        TransactionSendResult sendResult = future.get();
        log.info("sendResult: " + JSONUtil.toJsonStr(sendResult));

        log.info(JSONUtil.toJsonStr(sendResult));
        log.info("saveOrder2 end");
        return R.ok();
    }

}
