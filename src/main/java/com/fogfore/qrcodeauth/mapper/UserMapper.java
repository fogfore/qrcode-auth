package com.fogfore.qrcodeauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fogfore.qrcodeauth.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
