package com.tony.order.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tony.order.service.domain.Order;
import com.tony.order.service.service.OrderService;
import com.tony.order.service.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
* @author TonyHu
* @description 针对表【tb_order】的数据库操作Service实现
* @createDate 2023-03-21 14:57:02
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




