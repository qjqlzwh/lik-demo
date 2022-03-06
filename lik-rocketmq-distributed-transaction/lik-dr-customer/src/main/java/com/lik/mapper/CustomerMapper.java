package com.lik.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lik.entity.customer.Customer;

import java.math.BigDecimal;

/**
 * <p>
 * 客户 Mapper 接口
 * </p>
 *
 * @author lik
 * @since 2022-02-04
 */
public interface CustomerMapper extends BaseMapper<Customer> {

    int updateCustomerAmount(Long customerId, BigDecimal amount);
}
