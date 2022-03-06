package com.lik.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lik.entity.order.Order;
import com.lik.entity.order.OrderItem;
import com.lik.entity.product.ProductInventory;
import com.lik.entity.product.ProductInventoryLog;
import com.lik.enums.BusinessType;
import com.lik.mapper.ProductInventoryMapper;
import com.lik.service.IProductInventoryLogService;
import com.lik.service.IProductInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>
 * 产品库存 服务实现类
 * </p>
 *
 * @author lik
 * @since 2022-02-04
 */
@Slf4j
@Service
public class ProductInventoryServiceImpl extends ServiceImpl<ProductInventoryMapper, ProductInventory> implements IProductInventoryService {

    @Autowired
    private IProductInventoryLogService productInventoryLogService;

    @Override
    public Integer subInventory(Long productId, BigDecimal qty) {
        return baseMapper.updateInventory(productId, qty);
    }

    /**
     * 扣减库存
     *
     * @param order
     */
    @Override
    @Transactional
    public void subInventory(Order order) {
        // 库存幂等处理
        long count = productInventoryLogService.count(Wrappers.<ProductInventoryLog>lambdaQuery()
                .eq(ProductInventoryLog::getTargetId, order.getId())
                .eq(ProductInventoryLog::getTargetType, BusinessType.ORDER.getVal())
                .lt(ProductInventoryLog::getQuantity, 0));
        if (count != 0) {
            log.info("订单库存日志已经存在，Body：" + JSONUtil.toJsonStr(order));
            return;
        }
        for (OrderItem orderItem : order.getOrderItems()) {
            // 更新库存
            int count2 = baseMapper.updateInventory(orderItem.getProductId(), orderItem.getQuantity().negate());
            String msg = "产品【"+ orderItem.getProductName() +"】";
            Assert.isTrue(count2 != 0, msg + "扣减库存失败！");
            // 写库存日志
            ProductInventoryLog inventoryLog = new ProductInventoryLog();
            inventoryLog.setProductId(orderItem.getProductId());
            inventoryLog.setQuantity(orderItem.getQuantity().negate());
            inventoryLog.setTargetId(order.getId());
            inventoryLog.setTargetItemId(orderItem.getId());
            inventoryLog.setTargetType(BusinessType.ORDER.getVal());
            inventoryLog.setMemo("订单扣减库存");
            boolean suc = productInventoryLogService.save(inventoryLog);
            Assert.isTrue(suc, msg + "扣减库存写日志失败！");
        }
    }

    /**
     * 增加库存
     *
     * @param order
     */
    @Override
    @Transactional
    public void addInventory(Order order) {
        // 库存幂等处理
        long count = productInventoryLogService.count(Wrappers.<ProductInventoryLog>lambdaQuery()
                .eq(ProductInventoryLog::getTargetId, order.getId())
                .eq(ProductInventoryLog::getTargetType, BusinessType.ORDER.getVal())
                .lt(ProductInventoryLog::getQuantity, 0));
        if (count == 0) {
            log.info("订单没有扣过库存，不需要回滚库存，Body：" + JSONUtil.toJsonStr(order));
            return;
        }
        count = productInventoryLogService.count(Wrappers.<ProductInventoryLog>lambdaQuery()
                .eq(ProductInventoryLog::getTargetId, order.getId())
                .eq(ProductInventoryLog::getTargetType, BusinessType.ORDER.getVal())
                .gt(ProductInventoryLog::getQuantity, 0));
        if (count != 0) {
            log.info("订单库存日志已经存在，Body：" + JSONUtil.toJsonStr(order));
            return;
        }
        for (OrderItem orderItem : order.getOrderItems()) {
            // 更新库存
            int count2 = baseMapper.updateInventory(orderItem.getProductId(), orderItem.getQuantity());
            String msg = "产品【"+ orderItem.getProductName() +"】";
            Assert.isTrue(count2 != 0, msg + "回滚库存失败！");
            // 写库存日志
            ProductInventoryLog inventoryLog = new ProductInventoryLog();
            inventoryLog.setProductId(orderItem.getProductId());
            inventoryLog.setQuantity(orderItem.getQuantity());
            inventoryLog.setTargetId(order.getId());
            inventoryLog.setTargetItemId(orderItem.getId());
            inventoryLog.setTargetType(BusinessType.ORDER.getVal());
            inventoryLog.setMemo("订单回滚库存");
            boolean suc = productInventoryLogService.save(inventoryLog);
            Assert.isTrue(suc, msg + "回滚库存写日志失败！");
        }
    }
}
