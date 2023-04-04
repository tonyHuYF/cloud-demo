package com.tony.hotel.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDoc {
    /**
     * 酒店id
     */
    private Long id;
    /**
     * 酒店名称
     */
    private String name;
    /**
     * 酒店地址
     */
    private String address;
    /**
     * 酒店价格
     */
    private Integer price;
    /**
     * 酒店评分
     */
    private Integer score;
    /**
     * 酒店品牌
     */
    private String brand;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 酒店星级，1星到5星，1钻到5钻
     */
    private String starName;
    /**
     * 商圈
     */
    private String business;
    /**
     * 范围
     */
    private String location;
    /**
     * 酒店图片
     */
    private String pic;

}
