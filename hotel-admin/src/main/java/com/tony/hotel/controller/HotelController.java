package com.tony.hotel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tony.hotel.domain.Hotel;
import com.tony.hotel.domain.PageResult;
import com.tony.hotel.service.HotelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.InvalidParameterException;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Resource
    private HotelService hotelService;

    @GetMapping("/{id}")
    public Hotel queryById(@PathVariable("id") Long id){
        return hotelService.getById(id);
    }

    @GetMapping("/list")
    public PageResult hotelList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "1") Integer size
    ){
        Page<Hotel> result = hotelService.page(new Page<>(page, size));

        return new PageResult(result.getTotal(), result.getRecords());
    }

    @PostMapping
    public void saveHotel(@RequestBody Hotel hotel){
        hotelService.save(hotel);
    }

    @PutMapping()
    public void updateById(@RequestBody Hotel hotel){
        if (hotel.getId() == null) {
            throw new InvalidParameterException("id不能为空");
        }
        hotelService.updateById(hotel);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        hotelService.removeById(id);
    }
}
