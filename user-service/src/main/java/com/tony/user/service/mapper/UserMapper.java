package com.tony.user.service.mapper;

import com.tony.user.service.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author TonyHu
* @description 针对表【tb_user】的数据库操作Mapper
* @createDate 2023-03-21 14:50:38
* @Entity com.tony.user.service.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




