package com.tony.feign.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
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
