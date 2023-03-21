package com.tony.user.service.controller;

import com.alibaba.fastjson.JSON;
import com.tony.user.service.domain.User;
import com.tony.user.service.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return JSON.toJSONString(user);
    }
}
