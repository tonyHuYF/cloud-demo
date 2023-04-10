package com.tony.order.service.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.tony.order.service.domain.Order;
import com.tony.order.service.service.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private RabbitTemplate rabbitTemplate;

//    @GetMapping("/test")
//    public void testMQ() throws InterruptedException {
//        String message = "hello , every one!!!";
//        String exchangeName = "fanout.exchange";
//        rabbitTemplate.convertAndSend(exchangeName, "", message);
//
//        System.out.println("发送mq_fanout成功");
//    }


//    @GetMapping("/test")
//    public void testMQ() throws InterruptedException {
//        String message = "hello , mq!!!____";
//        String queue = "simple.queue";
//        for (int i = 1; i <= 50; i++) {
//            rabbitTemplate.convertAndSend(queue, message + i);
//            Thread.sleep(20);
//        }
//
//        System.out.println("发送mq成功");
//    }

//    @GetMapping("/test")
//    public void testMQ() throws InterruptedException {
//        String routingKey = "yellow";
//        String message = "hello , direct one__" + routingKey + "   !!!";
//        String exchangeName = "direct_exchange";
//        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
//
//        System.out.println("发送mq_direct成功");
//    }

//    @GetMapping("/test")
//    public void testMQ() throws InterruptedException {
//        String routingKey = "china.weather";
//        String message = "hello , topic one__" + routingKey + "   !!!";
//        String exchangeName = "topic_exchange";
//        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
//
//        System.out.println("发送mq_topic成功");
//    }

    @GetMapping("/test")
    public void testMQ() throws InterruptedException {
        String queue = "object.queue";

        Map<String, Object> map = new HashMap<>();
        map.put("name", "托尼");
        map.put("age", 21);

        rabbitTemplate.convertAndSend(queue, map);

        System.out.println("发送mq_object成功");
    }


    @SentinelResource("hot")
    @GetMapping("/{id}")
    public Order getOrderWithUser(@PathVariable Long id) {
        Order order = orderService.getOrderWithUser(id);
        return order;
    }

    @GetMapping("/query")
    public String query() {
        orderService.queryGoods();
        return "查询订单成功！";
    }

    @GetMapping("/save")
    public String save() {
        orderService.queryGoods();
        return "插入订单成功！";
    }

    @GetMapping("update")
    public String update() {
        return "更新订单成功！";
    }

}
