package com.fogfore.qrcodeauth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fogfore.qrcodeauth.entity.UserAddress;
import com.fogfore.qrcodeauth.entity.UserAddressAuth;
import com.fogfore.qrcodeauth.mapper.UserAddressMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserAddressService extends ServiceImpl<UserAddressMapper, UserAddress> {
    public List<UserAddressAuth> listAddrs(Integer userId) {
        return baseMapper.listAddrs(userId);
    }

    public UserAddress get(Integer userId, Integer addrId) {
        QueryWrapper<UserAddress> wrapper = new QueryWrapper<UserAddress>()
                .eq("user_id", userId)
                .eq("address_id", addrId);
        return baseMapper.selectOne(wrapper);
    }

    public int del(Integer userId, Integer addrId) {
        UpdateWrapper<UserAddress> wrapper = new UpdateWrapper<UserAddress>()
                .eq("user_id", userId)
                .eq("address_id", addrId);
        return baseMapper.delete(wrapper);
    }

    public int del(Integer addrId) {
        UpdateWrapper<UserAddress> wrapper = new UpdateWrapper<UserAddress>()
                .eq("address_id", addrId);
        return baseMapper.delete(wrapper);
    }
}
