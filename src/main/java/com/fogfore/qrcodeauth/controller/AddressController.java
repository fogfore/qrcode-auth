package com.fogfore.qrcodeauth.controller;

import com.alibaba.fastjson.JSONObject;
import com.fogfore.qrcodeauth.annotation.LoginRequire;
import com.fogfore.qrcodeauth.config.UserThreadLocal;
import com.fogfore.qrcodeauth.entity.*;
import com.fogfore.qrcodeauth.service.AddressService;
import com.fogfore.qrcodeauth.service.UserAddressService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addr")
public class AddressController {
    @Autowired
    private UserThreadLocal userThreadLocal;
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private AddressService addressService;

    @LoginRequire
    @GetMapping("/get/{addrId}")
    public RespBody getAddrInfo(@PathVariable("addrId") Integer addrId) {
        User user = userThreadLocal.get();
        UserAddress userAddress = userAddressService.get(user.getId(), addrId);
        if (ObjectUtils.isEmpty(userAddress)) {
            return RespBody.argsError("没有访问权限");
        }
        Address addr = addressService.getById(addrId);
        return RespBody.ok(addr);
    }

    @LoginRequire
    @PostMapping("/add")
    public RespBody addAddr(@RequestBody JSONObject json) {
        String addrName = json.getString("addrName");
        String addrInfo = json.getString("addrInfo");
        String addrDesc = json.getString("addrDesc");
        if (StringUtils.isEmpty(addrName) || StringUtils.isEmpty(addrInfo)) {
            return RespBody.argsError("参数不能为空");
        }

        Address address = Address.builder()
                .name(addrName)
                .location(addrInfo)
                .description(addrDesc).build();
        addressService.save(address);

        User user = userThreadLocal.get();
        UserAddress userAddress = UserAddress.builder()
                .addressId(address.getId())
                .userId(user.getId())
                .authority("2").build();
        userAddressService.save(userAddress);

        return RespBody.ok("添加地址成功");
    }

    @LoginRequire
    @GetMapping("/list")
    public RespBody listAddr() {
        User user = userThreadLocal.get();
        List<UserAddressAuth> addrs = userAddressService.listAddrs(user.getId());
        return RespBody.ok(addrs);
    }

    @LoginRequire
    @GetMapping("/del/{addrId}")
    public RespBody delAddr(@PathVariable("addrId") Integer addrId) {
        User user = userThreadLocal.get();
        UserAddress hasAuth = userAddressService.get(user.getId(), addrId);
        if (ObjectUtils.isEmpty(hasAuth) || !"2".equals(hasAuth.getAuthority())) {
            return RespBody.argsError("您没有操作权限");
        }

        addressService.removeById(addrId);
        userAddressService.del(addrId);
        return RespBody.ok("删除成功");
    }
}
