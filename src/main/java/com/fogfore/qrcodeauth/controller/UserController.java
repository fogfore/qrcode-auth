package com.fogfore.qrcodeauth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fogfore.qrcodeauth.annotation.LoginRequire;
import com.fogfore.qrcodeauth.config.UserThreadLocal;
import com.fogfore.qrcodeauth.entity.RespBody;
import com.fogfore.qrcodeauth.entity.User;
import com.fogfore.qrcodeauth.service.RedisService;
import com.fogfore.qrcodeauth.service.UserService;
import com.fogfore.qrcodeauth.service.WeChatService;
import com.fogfore.qrcodeauth.utils.CommonUtils;
import com.fogfore.qrcodeauth.utils.RedisUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
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

    @RequestMapping("/login")
    @ResponseBody
    public RespBody login(@RequestBody JSONObject json) {
        String code = json.getString("code");
        User user = json.getObject("user", User.class);
        if (ObjectUtils.isEmpty(user) || StringUtils.isEmpty(code)) {
            return RespBody.argsError("登录失败");
        }
        JSONObject sessionJson = weChatService.getCode2Session(code);
        String openid = sessionJson.getString("openid");
        String sessionKey = sessionJson.getString("session_key");

        // 没有用户 创建用户
        User oldUser = userService.selectByOpenid(openid);
        String uid = oldUser.getUid();
        if (ObjectUtils.isEmpty(oldUser)) {
            user.setOpenid(openid);
            user.setSessionKey(sessionKey);
            uid = CommonUtils.getUUID();
            user.setUid(uid);
            userService.save(user);
        }

        String skey = CommonUtils.getUUID();
        String value = JSON.toJSONString(user);
        redisService.setEx(RedisUtils.getSessionKey(skey), value, 1, TimeUnit.DAYS);
        Map<String, Object> data = new HashMap<>();
        data.put("skey", skey);
        data.put("uid", uid);
        return RespBody.ok(data);
    }
}
