package com.fogfore.qrcodeauth.config;

import com.fogfore.qrcodeauth.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserThreadLocal {
    private static ThreadLocal<User> local = new ThreadLocal<>();

    public User get() {
        return local.get();
    }

    public void set(User user) {
        local.set(user);
    }
}
