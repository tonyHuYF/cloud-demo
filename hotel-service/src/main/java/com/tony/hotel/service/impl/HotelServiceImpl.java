package com.tony.hotel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tony.hotel.domain.Hotel;
import com.tony.hotel.service.HotelService;
import com.tony.hotel.mapper.HotelMapper;
import org.springframework.stereotype.Service;

/**
* @author TonyHu
* @description 针对表【tb_hotel】的数据库操作Service实现
* @createDate 2023-03-31 15:28:49
*/
@Service
public class HotelServiceImpl extends ServiceImpl<HotelMapper, Hotel>
    implements HotelService{

}




