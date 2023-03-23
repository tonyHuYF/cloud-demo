package com.tony.user.service.controller;

import com.tony.user.service.config.PatternProperties;
import com.tony.user.service.domain.User;
import com.tony.user.service.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private PatternProperties patternProperties;

    @GetMapping("/prop")
    public PatternProperties patternProperties() {
        return patternProperties;
    }

    @GetMapping("/now")
    public String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(patternProperties.getDataFormat()));
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id, @RequestHeader(value = "Truth", required = false) String truth) {
        System.out.println(truth);
        User user = userService.getById(id);
        return user;
    }
}
