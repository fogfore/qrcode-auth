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
import com.fogfore.qrcodeauth.vo.UserVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
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
        if (ObjectUtils.isEmpty(oldUser)) {
            user.setOpenid(openid);
            user.setSessionKey(sessionKey);
            user.setUid(CommonUtils.getUUID());
            userService.save(user);
            oldUser = user;
        }

        String skey = CommonUtils.getUUID();
        String value = JSON.toJSONString(oldUser);
        redisService.setEx(RedisUtils.getSessionKey(skey), value, 1, TimeUnit.DAYS);
        Map<String, Object> data = new HashMap<>();
        data.put("skey", skey);
        data.put("uid", oldUser.getUid());
        return RespBody.ok(data);
    }

    @LoginRequire
    @GetMapping("/get/userinfo")
    public RespBody getUserInfo(String uid) {
        User user = userService.selectByUid(uid);
        if (ObjectUtils.isEmpty(user)) {
            return RespBody.argsError("该用户不存在");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return RespBody.ok(userVo);
    }

    @LoginRequire
    @GetMapping("/agree/visit")
    public RespBody argeeVisit(String uid) {
        User visitor = userService.selectByUid(uid);
        if (ObjectUtils.isEmpty(visitor)) {
            return RespBody.argsError("该用户不存在");
        }
        User user = userThreadLocal.get();

        String key = RedisUtils.getVisitorsKey(user.getUid());
        redisService.sAdd(key, uid);
        return RespBody.ok("已为该用户设置访问权限");
    }
}
