package com.tony.order.service.mapper;

import com.tony.order.service.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author TonyHu
* @description 针对表【tb_order】的数据库操作Mapper
* @createDate 2023-03-21 14:57:02
* @Entity com.tony.order.service.domain.Order
*/
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}




