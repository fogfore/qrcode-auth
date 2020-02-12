package com.fogfore.qrcodeauth.controller;

import com.fogfore.qrcodeauth.annotation.LoginRequire;
import com.fogfore.qrcodeauth.config.UserThreadLocal;
import com.fogfore.qrcodeauth.entity.Address;
import com.fogfore.qrcodeauth.entity.RespBody;
import com.fogfore.qrcodeauth.entity.User;
import com.fogfore.qrcodeauth.entity.UserAddress;
import com.fogfore.qrcodeauth.service.AddressService;
import com.fogfore.qrcodeauth.service.UserAddressService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/addr")
public class AddressController {
    @Autowired
    private UserThreadLocal userThreadLocal;
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private AddressService addressService;

    @LoginRequire
    @GetMapping("/get/addrinfo/{addrId}")
    @ResponseBody
    public RespBody getAddrInfo(@PathVariable("addrId") Integer addrId) {
        Address addr = addressService.getById(addrId);
        return RespBody.ok(addr);
    }
}
