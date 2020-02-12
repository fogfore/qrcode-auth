package com.fogfore.qrcodeauth.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fogfore.qrcodeauth.entity.Address;
import com.fogfore.qrcodeauth.mapper.AddressMapper;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends ServiceImpl<AddressMapper, Address> {
}
