package com.tony.hotel.service;

import com.tony.hotel.domain.Hotel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tony.hotel.domain.TableResultBean;
import com.tony.hotel.domain.param.QueryParam;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* @author TonyHu
* @description 针对表【tb_hotel】的数据库操作Service
* @createDate 2023-03-31 15:28:49
*/
public interface HotelService extends IService<Hotel> {

    TableResultBean listPage(QueryParam param) throws IOException;

    Map<String, List<String>> filters(QueryParam param)throws IOException;

    List<String> getSuggestions(String prefix) throws IOException;

}
