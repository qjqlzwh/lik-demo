package com.lik.controller;

import com.lik.entity.customer.Customer;
import com.lik.entity.customer.CustomerAmountLog;
import com.lik.resp.R;
import com.lik.service.ICustomerAmountLogService;
import com.lik.service.ICustomerService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ICustomerAmountLogService customerAmountLogService;

    @PostMapping("save")
    public R save(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);
        return R.ok();
    }

    @GetMapping("get/{id}")
    public R<Customer> get(@PathVariable("id") Long id) {
        Assert.notNull(id, "id 不存在！");
        Customer customer = customerService.getById(id);
        return R.ok().data(customer);
    }

    /**
     * 更新客户余额
     */
    @PostMapping("updateCustomerAmount")
    public R updateCustomerAmount(@RequestBody CustomerAmountLog customerAmountLog) {
        customerService.updateCustomerAmount(customerAmountLog);
        return R.ok();
    }
}
