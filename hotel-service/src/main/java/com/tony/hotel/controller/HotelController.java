package com.tony.hotel.controller;

import com.tony.hotel.constants.HotelConstants;
import com.tony.hotel.domain.Hotel;
import com.tony.hotel.service.HotelService;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Resource
    private HotelService hotelService;

    @GetMapping
    public String getAll() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create("http://localhost:9200")));

        CreateIndexRequest request = new CreateIndexRequest("hotel");

        request.source(HotelConstants.MAPPING_TEMPLATE, XContentType.JSON);

        client.indices().create(request, RequestOptions.DEFAULT);

//        List<Hotel> list = hotelService.list();
//        return list;

        return "success";
    }

    @GetMapping("/one")
    public Hotel getOne() {
        List<Hotel> list = hotelService.list();
        Hotel hotel = list.get(0);
        return hotel;
    }
}
