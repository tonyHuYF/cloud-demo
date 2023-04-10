package com.tony.order.service.service;

import com.tony.order.service.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author TonyHu
* @description 针对表【tb_order】的数据库操作Service
* @createDate 2023-03-21 14:57:02
*/
public interface OrderService extends IService<Order> {

    Order getOrderWithUser(Long id);

    void queryGoods();

}
