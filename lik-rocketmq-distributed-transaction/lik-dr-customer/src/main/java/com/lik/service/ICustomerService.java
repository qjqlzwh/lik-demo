package com.lik.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lik.entity.customer.Customer;
import com.lik.entity.customer.CustomerAmountLog;

/**
 * <p>
 * 客户 服务类
 * </p>
 *
 * @author lik
 * @since 2022-02-04
 */
public interface ICustomerService extends IService<Customer> {

    void saveCustomer(Customer customer);

    /**
     * 更新客户余额
     * @param customerAmountLog
     */
    void updateCustomerAmount(CustomerAmountLog customerAmountLog);

    /**
     * 回滚客户余额
     * @param customerAmountLog
     */
    void rollbackCustomerAmount(CustomerAmountLog customerAmountLog);
}
