package com.tony.feign.fallback;

import com.tony.feign.client.UserClient;
import com.tony.feign.domain.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable throwable) {
        return new UserClient() {
            @Override
            public User getUserById(Long id) {
                log.error("查询用户异常", throwable);
                return new User();
            }
        };
    }
}
