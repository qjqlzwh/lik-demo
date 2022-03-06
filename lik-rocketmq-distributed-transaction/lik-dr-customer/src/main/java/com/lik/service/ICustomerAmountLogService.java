package com.lik.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lik.entity.customer.CustomerAmountLog;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lik
 * @since 2022-02-13
 */
public interface ICustomerAmountLogService extends IService<CustomerAmountLog> {

    void subCustomerAmount(CustomerAmountLog customerAmountLog);
}
