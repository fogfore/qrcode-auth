package com.fogfore.qrcodeauth.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fogfore.qrcodeauth.utils.HttpClientUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeChatService {
    private static final String appid = "wxbfc9c10491e1b240";
    private static final String secret = "98532f8fa6c9b9229d95f9d2e52846ea";
    private static final String code2SessionUrl = "https://api.weixin.qq.com/sns/jscode2session";

    public JSONObject getCode2Session(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", appid);
        map.put("secret", secret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        return JSON.parseObject(HttpClientUtils.post(code2SessionUrl, map));
    }
}
