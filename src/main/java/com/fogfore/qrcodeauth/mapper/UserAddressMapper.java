package com.fogfore.qrcodeauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fogfore.qrcodeauth.entity.UserAddress;
import com.fogfore.qrcodeauth.entity.UserAddressAuth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAddressMapper extends BaseMapper<UserAddress> {
    List<UserAddressAuth> listAddrs(Integer userId);
}
