package com.tony.order.service.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tony.feign.client.UserClient;
import com.tony.feign.domain.User;
import com.tony.order.service.domain.Order;
import com.tony.order.service.mapper.OrderMapper;
import com.tony.order.service.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author TonyHu
* @description 针对表【tb_order】的数据库操作Service实现
* @createDate 2023-03-21 14:57:02
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

//    @Resource
//    private RestTemplate restTemplate;

    @Resource
    private UserClient userClient;

    @Override
    public Order getOrderWithUser(Long id) {
        Order order = getById(id);

        User user = userClient.getUserById(order.getUserId());

        order.setUser(user);

        return order;
    }


//    @Override
//    public Order getOrderWithUser(Long id) {
//        Order order = getById(id);
//
//        String url = "http://userservice/user/"+order.getUserId();
//
//        User user = restTemplate.getForObject(url, User.class);
//
//        order.setUser(user);
//
//        return order;
//    }


    @Override
    @SentinelResource("goods")
    public void queryGoods() {
        System.out.println("查询商品！");
    }
}




