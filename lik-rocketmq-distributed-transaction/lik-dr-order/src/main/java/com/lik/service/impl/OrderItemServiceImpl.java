package com.lik.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lik.entity.order.OrderItem;
import com.lik.mapper.OrderItemMapper;
import com.lik.service.IOrderItemService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单明细 服务实现类
 * </p>
 *
 * @author lik
 * @since 2022-02-04
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements IOrderItemService {

}
