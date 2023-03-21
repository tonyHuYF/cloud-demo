package com.tony.order.service.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @TableName tb_order
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_order")
public class Order implements Serializable {

    /**
     * 订单id
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品价格
     */
    private Long price;
    /**
     * 商品数量
     */
    private Integer num;

    @TableField(exist = false)
    private User user;

}
