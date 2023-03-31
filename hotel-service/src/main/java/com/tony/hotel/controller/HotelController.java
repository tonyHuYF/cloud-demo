package com.tony.hotel.controller;

import com.tony.hotel.domain.Hotel;
import com.tony.hotel.service.HotelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Resource
    private HotelService hotelService;

    @GetMapping
    public List<Hotel> getAll() {
        List<Hotel> list = hotelService.list();
        return list;
    }

    @GetMapping("/one")
    public Hotel getOne() {
        List<Hotel> list = hotelService.list();
        Hotel hotel = list.get(0);
        return hotel;
    }
}
