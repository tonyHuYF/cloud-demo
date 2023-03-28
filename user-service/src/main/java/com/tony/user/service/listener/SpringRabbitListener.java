package com.tony.user.service.listener;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class SpringRabbitListener {

//    @RabbitListener(queues = "simple.queue")
//    public void receiveMsg(String msg) {
//        System.out.println("receive msg:" + msg);
//    }

//    @RabbitListener(queues = "simple.queue")
//    public void receiveMsgWorkQueue1(String msg) throws InterruptedException {
//
//        System.out.println("MQ1_receive msg:" + msg + "     " + LocalDateTime.now());
//        Thread.sleep(20);
//    }
//
//    @RabbitListener(queues = "simple.queue")
//    public void receiveMsgWorkQueue2(String msg) throws InterruptedException {
//        System.out.println("MQ2________receive msg:" + msg + "     " + LocalDateTime.now());
//        Thread.sleep(200);
//    }

    @RabbitListener(queues = "fanout.queue1")
    public void receiveMsgWorkQueue1(String msg) throws InterruptedException {

        System.out.println("fanout.queue1_receive msg:" + msg + "     " + LocalDateTime.now());
        Thread.sleep(20);
    }

    @RabbitListener(queues = "fanout.queue2")
    public void receiveMsgWorkQueue2(String msg) throws InterruptedException {
        System.out.println("fanout.queue2________receive msg:" + msg + "     " + LocalDateTime.now());
        Thread.sleep(20);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),
            exchange = @Exchange(name = "direct_exchange", type = "direct"),
            key = {"red", "yellow"}))
    public void receiveMsgDirectQueue1(String msg) throws InterruptedException {
        System.out.println("direct.queue1________receive msg:" + msg + "     " + LocalDateTime.now());
        Thread.sleep(20);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2"),
            exchange = @Exchange(name = "direct_exchange", type = "direct"),
            key = {"red", "blue"}
    ))
    public void receiveMsgDirectQueue2(String msg) throws InterruptedException {
        System.out.println("direct.queue2________receive msg:" + msg + "     " + LocalDateTime.now());
        Thread.sleep(20);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue1"),
            exchange = @Exchange(name = "topic_exchange", type = "topic"),
            key = "china.#"
    ))
    public void receiveMsgTopicQueue1(String msg) throws InterruptedException {
        System.out.println("topic.queue1________receive msg:" + msg + "     " + LocalDateTime.now());
        Thread.sleep(20);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue2"),
            exchange = @Exchange(name = "topic_exchange", type = "topic"),
            key = "#.news"
    ))
    public void receiveMsgTopicQueue2(String msg) throws InterruptedException {
        System.out.println("topic.queue2________receive msg:" + msg + "     " + LocalDateTime.now());
        Thread.sleep(20);
    }


    @RabbitListener(queues = "object.queue")
    public void receiveMsgObjectQueue(Map<String,Object> map) throws InterruptedException {
        System.out.println("object.queue________receive msg:" + map + "     " + LocalDateTime.now());
        Thread.sleep(20);
    }

}
