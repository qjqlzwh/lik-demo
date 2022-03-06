package com.lik.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalFeignConfiguration {

    @Bean
    public Logger.Level level() {
        return Logger.Level.FULL;
    }
}
