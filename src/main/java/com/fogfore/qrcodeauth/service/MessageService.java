package com.fogfore.qrcodeauth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fogfore.qrcodeauth.entity.Message;
import com.fogfore.qrcodeauth.mapper.MessageMapper;
import com.fogfore.qrcodeauth.vo.MessageVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService extends ServiceImpl<MessageMapper, Message> {
    public boolean smartInsert(Message message) {
        QueryWrapper<Message> wrapper = new QueryWrapper<Message>()
                .eq("from_uid", message.getFromUid())
                .eq("type", message.getType())
                .eq("address_id", message.getAddressId());
        Message m = baseMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(m)) {
            baseMapper.insert(message);
        }
        return true;
    }

    public List<MessageVo> listMessages(Integer uid) {

        return null;
    }
}
