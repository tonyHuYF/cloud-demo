package com.tony.user.service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanout.exchange");
    }

    @Bean
    public Queue queue1(){
        return new Queue("fanout.queue1");
    }

    @Bean
    public Queue queue2(){
        return new Queue("fanout.queue2");
    }

    @Bean
    public Binding binding1(Queue queue1 ,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue1).to(fanoutExchange);
    }

    @Bean
    public Binding binding2(Queue queue2 ,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue2).to(fanoutExchange);
    }

    @Bean
    public Queue objectQueue(){
        return new Queue("object.queue");
    }
}
