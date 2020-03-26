package com.fogfore.qrcodeauth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fogfore.qrcodeauth.annotation.LoginRequire;
import com.fogfore.qrcodeauth.config.UserThreadLocal;
import com.fogfore.qrcodeauth.entity.RespBody;
import com.fogfore.qrcodeauth.entity.User;
import com.fogfore.qrcodeauth.entity.UserAddress;
import com.fogfore.qrcodeauth.entity.UserAddressAuth;
import com.fogfore.qrcodeauth.service.RedisService;
import com.fogfore.qrcodeauth.service.UserAddressService;
import com.fogfore.qrcodeauth.service.UserService;
import com.fogfore.qrcodeauth.service.WeChatService;
import com.fogfore.qrcodeauth.utils.CommonUtils;
import com.fogfore.qrcodeauth.utils.RedisUtils;
import com.fogfore.qrcodeauth.vo.UserAuthVo;
import com.fogfore.qrcodeauth.vo.UserDetailVo;
import com.fogfore.qrcodeauth.vo.UserInfoVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private WeChatService weChatService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserThreadLocal userThreadLocal;
    @Autowired
    private UserAddressService userAddressService;

    @PostMapping("/login")
    @ResponseBody
    public RespBody login(@RequestBody JSONObject json) {
        String code = json.getString("code");
        User user = json.getObject("userInfo", User.class);
        if (ObjectUtils.isEmpty(user) || StringUtils.isEmpty(code)) {
            return RespBody.argsError("登录失败");
        }
        JSONObject sessionJson = weChatService.getCode2Session(code);
        String openid = sessionJson.getString("openid");
        String sessionKey = sessionJson.getString("session_key");

        // 没有用户 创建用户
        User oldUser = userService.selectByOpenid(openid);
        if (ObjectUtils.isEmpty(oldUser)) {
            user.setOpenid(openid);
            user.setSessionKey(sessionKey);
            userService.save(user);
            oldUser = user;
        }

        String skey = CommonUtils.getUUID();
        redisService.setEx(RedisUtils.getSessionKey(skey), openid, 1, TimeUnit.HOURS);
        Map<String, Object> data = new HashMap<>();
        data.put("skey", skey);
        data.put("uid", oldUser.getId());
        return RespBody.ok(data);
    }

    @LoginRequire
    @PostMapping("/auth/visit")
    public RespBody authVisit(@RequestBody JSONObject json) {
        String credential = json.getString("credential");
        String addrId = json.getString("addrId");
        if (StringUtils.isEmpty(credential) || StringUtils.isEmpty(addrId)) {
            return RespBody.argsError("参数错误");
        }

        String aid = redisService.get(RedisUtils.getVisitorKey(credential));
        if (Objects.equals(addrId, aid)) {
            return RespBody.ok("同意访问");
        } else {
            return RespBody.argsError("该用户没有访问权限");
        }
    }

    @LoginRequire
    @GetMapping("/get/userinfo")
    public RespBody getUserInfo() {
        User user = userThreadLocal.get();
        user = userService.getById(user.getId());
        UserInfoVo userVo = new UserInfoVo();
        BeanUtils.copyProperties(user, userVo);
        return RespBody.ok(userVo);
    }

    @LoginRequire
    @PostMapping("/update/userinfo")
    public RespBody updateUserInfo(@RequestBody JSONObject json) {
        Integer uid = userThreadLocal.get().getId();
        UserInfoVo userVo = json.getObject("userInfo", UserInfoVo.class);
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        user.setId(uid);
        userService.update(user);
        return RespBody.ok("更新用户信息成功");
    }

    @LoginRequire
    @PostMapping("/fuzzy/query/user")
    public RespBody fuzzyQuery(@RequestBody JSONObject json) {
        String value = json.getString("value");
        Integer addrId = json.getInteger("addrId");
        if (StringUtils.isEmpty(value) || ObjectUtils.isEmpty(addrId)) {
            return RespBody.argsError("参数错误");
        }
        List<UserDetailVo> userList = userService.fuzzyQuery(value, addrId);
        return RespBody.ok(userList);
    }

    @LoginRequire
    @PostMapping("/add/visitor")
    public RespBody addVisitor(@RequestBody JSONObject json) {
        Integer visitorId = json.getInteger("visitorId");
        Integer addrId = json.getInteger("addrId");
        if (ObjectUtils.isEmpty(visitorId) || ObjectUtils.isEmpty(addrId)) {
            return RespBody.argsError("参数错误");
        }
        User user = userThreadLocal.get();
        UserAddress hasAuth = userAddressService.get(user.getId(), addrId);
        if (ObjectUtils.isEmpty(hasAuth) || !"2".equals(hasAuth.getAuthority())) {
            return RespBody.argsError("您没有操作权限");
        }
        UserAddress userAddress = new UserAddress(null, visitorId, addrId, "1");
        userAddressService.save(userAddress);
        return RespBody.ok("添加成功");
    }

    @LoginRequire
    @GetMapping("/list/visitors/{addrId}")
    public RespBody listVisitors(@PathVariable("addrId") Integer addrId) {
        List<UserAuthVo> list = userService.listVisitors(addrId);
        return RespBody.ok(list);
    }

    @LoginRequire
    @PostMapping("/del/visitor")
    public RespBody delVisitor(@RequestBody JSONObject json) {
        Integer visitorId = json.getInteger("visitorId");
        Integer addrId = json.getInteger("addrId");
        if (ObjectUtils.isEmpty(visitorId) || ObjectUtils.isEmpty(addrId)) {
            return RespBody.argsError("参数错误");
        }
        User user = userThreadLocal.get();
        UserAddress hasAuth = userAddressService.get(user.getId(), addrId);
        if (ObjectUtils.isEmpty(hasAuth) || !"2".equals(hasAuth.getAuthority())) {
            return RespBody.argsError("您没有操作权限");
        }
        userAddressService.del(visitorId, addrId);
        return RespBody.ok("删除成功");
    }
}