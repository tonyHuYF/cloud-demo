package com.tony.user.service.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* 
* @TableName tb_user
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user")
public class User implements Serializable {
    /**
    * 
    */
    @TableId
    private Long id;
    /**
    * 收件人
    */
    private String username;
    /**
    * 地址
    */
    private String address;

}
