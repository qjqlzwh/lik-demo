package com.lik.service.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lik.entity.customer.Customer;
import com.lik.entity.customer.CustomerAmountLog;
import com.lik.mapper.CustomerMapper;
import com.lik.service.ICustomerAmountLogService;
import com.lik.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 客户 服务实现类
 * </p>
 *
 * @author lik
 * @since 2022-02-04
 */
@Slf4j
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    @Autowired
    private ICustomerAmountLogService customerAmountLogService;

    @Override
    @Transactional
    public void saveCustomer(Customer customer) {
        Assert.notEmpty(customer.getCode(), "客户编码不能为空");
        Assert.notEmpty(customer.getName(), "客户名称不能为空");
        this.save(customer);
    }

    /**
     * 更新客户余额
     * @param customerAmountLog
     */
    @Override
    @Transactional
    public void updateCustomerAmount(CustomerAmountLog customerAmountLog) {
        // 余额幂等处理
        long count = customerAmountLogService.count(Wrappers.<CustomerAmountLog>lambdaQuery()
                .eq(CustomerAmountLog::getCustomerId, customerAmountLog.getCustomerId())
                .eq(CustomerAmountLog::getTargetId, customerAmountLog.getTargetId())
                .eq(CustomerAmountLog::getTargetType, customerAmountLog.getTargetType())
                .eq(CustomerAmountLog::getAmount, customerAmountLog.getAmount())); // 余额
        if (count != 0) {
            log.info("余额日志已经存在，Body：" + JSONUtil.toJsonStr(customerAmountLog));
            return ;
        }

        // 写日志, 可以对日志表中的customer_id, target_id, target_type, amount 4个字段设置成复合唯一键，避免重复扣
        boolean suc = customerAmountLogService.save(customerAmountLog);
        Assert.isTrue(suc, "写客户余额日志失败！");

        // 更新客户余额
        count = baseMapper.updateCustomerAmount(customerAmountLog.getCustomerId(), customerAmountLog.getAmount());
        Assert.isTrue(count != 0, "更新客户余额失败");

    }

    /**
     * 回滚客户余额
     *
     * @param customerAmountLog
     */
    @Override
    @Transactional
    public void rollbackCustomerAmount(CustomerAmountLog customerAmountLog) {
        long count = customerAmountLogService.count(Wrappers.<CustomerAmountLog>lambdaQuery()
                .eq(CustomerAmountLog::getCustomerId, customerAmountLog.getCustomerId())
                .eq(CustomerAmountLog::getTargetId, customerAmountLog.getTargetId())
                .eq(CustomerAmountLog::getTargetType, customerAmountLog.getTargetType())
                .eq(CustomerAmountLog::getAmount, customerAmountLog.getAmount().negate())); // 余额
        if (count == 0) {
            log.info("余额不存在扣减，不需要回滚，Body：" + JSONUtil.toJsonStr(customerAmountLog));
            return ;
        }
        this.updateCustomerAmount(customerAmountLog);
    }
}
