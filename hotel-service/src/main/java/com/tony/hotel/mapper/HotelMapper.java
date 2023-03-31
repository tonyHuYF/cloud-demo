package com.tony.hotel.mapper;

import com.tony.hotel.domain.Hotel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author TonyHu
* @description 针对表【tb_hotel】的数据库操作Mapper
* @createDate 2023-03-31 15:28:49
* @Entity com.tony.hotel.domain.Hotel
*/
@Mapper
public interface HotelMapper extends BaseMapper<Hotel> {

}




