package com.fogfore.qrcodeauth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fogfore.qrcodeauth.entity.User;
import com.fogfore.qrcodeauth.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    public User selectByOpenid(String openid) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("openid", openid);
        return baseMapper.selectOne(wrapper);
    }
}
