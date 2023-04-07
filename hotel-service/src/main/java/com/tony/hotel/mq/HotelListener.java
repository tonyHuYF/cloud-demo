package com.tony.hotel.mq;

import com.tony.hotel.constants.MqConstants;
import com.tony.hotel.service.HotelService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class HotelListener {

    @Resource
    private HotelService hotelService;

    @RabbitListener(queues = MqConstants.HOTEL_INSERT_QUEUE)
    public void insertOrUpdateListener(Long id){
        hotelService.insertById(id);
    }

    @RabbitListener(queues = MqConstants.HOTEL_DELETE_QUEUE)
    public void deleteListener(Long id){
        hotelService.deleteById(id);
    }

}
