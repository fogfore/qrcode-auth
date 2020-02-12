package com.fogfore.qrcodeauth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fogfore.qrcodeauth.entity.*;
import com.fogfore.qrcodeauth.mapper.UserMapper;
import com.fogfore.qrcodeauth.vo.UserDetailVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Autowired
    private AddressService addressService;
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private MessageService messageService;

    public User selectByOpenid(String openid) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("openid", openid);
        return baseMapper.selectOne(wrapper);
    }

    public boolean update(User user) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<User>()
                .eq("id", user.getId())
                .set(ObjectUtils.isNotEmpty(user.getRealName()), "real_name", user.getRealName())
                .set(ObjectUtils.isNotEmpty(user.getRealName()), "phone", user.getPhone());
        return update(user, wrapper);
    }

    public List<UserDetailVo> fuzzyQuery(String value, Integer addrId) {
        return baseMapper.fuzzyQuery(value, addrId);
    }
}
