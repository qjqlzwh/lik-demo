package com.lik.feign.customer;

import com.lik.entity.customer.CustomerAmountLog;
import com.lik.resp.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("lik-dr-customer")
public interface CustomerFeign {


    /**
     * 扣减余额
     */
    @PostMapping("/customer/updateCustomerAmount")
    R updateCustomerAmount(@RequestBody CustomerAmountLog customerAmountLog);
}
