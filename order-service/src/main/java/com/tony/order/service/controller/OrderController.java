package com.tony.order.service.controller;

import com.tony.order.service.domain.Order;
import com.tony.order.service.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/{id}")
    public Order getOrderWithUser(@PathVariable Long id) {
        Order order = orderService.getOrderWithUser(id);
        return order;
    }

}
