package com.fogfore.qrcodeauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fogfore.qrcodeauth.entity.Message;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}
