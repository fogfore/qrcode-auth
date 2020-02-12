package com.fogfore.qrcodeauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fogfore.qrcodeauth.entity.User;
import com.fogfore.qrcodeauth.vo.UserDetailVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<UserDetailVo> fuzzyQuery(String value, Integer addrId);
}
