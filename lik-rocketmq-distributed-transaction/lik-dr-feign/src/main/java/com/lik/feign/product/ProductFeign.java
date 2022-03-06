package com.lik.feign.product;

import com.lik.dto.product.ProductDTO;
import com.lik.entity.product.Product;
import com.lik.resp.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient("lik-dr-product")
public interface ProductFeign {

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/product/findById/{id}")
    Product findById(@PathVariable("id") Long id);

    @GetMapping("/product/findProduct")
    R findProduct(@RequestParam String name, @RequestParam String code);

    @GetMapping("/product/findProduct2")
    R findProduct2(@SpringQueryMap ProductDTO productDTO);

    @GetMapping("/product/findProduct3")
    R findProduct3(@RequestParam Map<String, Object> map);

    @PostMapping("/product/findProduct4")
    R findProduct4(@RequestBody ProductDTO productDTO);
}
