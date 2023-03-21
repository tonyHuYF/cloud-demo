package com.tony.user.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tony.user.service.domain.User;
import com.tony.user.service.service.UserService;
import com.tony.user.service.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author TonyHu
* @description 针对表【tb_user】的数据库操作Service实现
* @createDate 2023-03-21 14:50:38
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




