package com.lik.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lik.entity.customer.CustomerAmountLog;
import com.lik.mapper.CustomerAmountLogMapper;
import com.lik.service.ICustomerAmountLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lik
 * @since 2022-02-13
 */
@Service
public class CustomerAmountLogServiceImpl extends ServiceImpl<CustomerAmountLogMapper, CustomerAmountLog> implements ICustomerAmountLogService {

    @Override
    @Transactional
    public void subCustomerAmount(CustomerAmountLog customerAmountLog) {

    }
}
