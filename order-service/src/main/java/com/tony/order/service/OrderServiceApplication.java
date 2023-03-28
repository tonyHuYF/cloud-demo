package com.tony.order.service;

import com.tony.feign.client.UserClient;
import com.tony.feign.config.FeignClientConfiguration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableEurekaClient
@EnableFeignClients(clients = UserClient.class, defaultConfiguration = FeignClientConfiguration.class)
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate(){
//        return new RestTemplate();
//    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
