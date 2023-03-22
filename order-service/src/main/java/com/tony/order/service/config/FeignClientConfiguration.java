package com.tony.order.service.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class FeignClientConfiguration {
    @Bean
    public Logger.Level loggerLevel(){
        return Logger.Level.BASIC;
    }
}
