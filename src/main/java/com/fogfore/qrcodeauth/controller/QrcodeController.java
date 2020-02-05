package com.fogfore.qrcodeauth.controller;

import com.alibaba.fastjson.JSON;
import com.fogfore.qrcodeauth.annotation.LoginRequire;
import com.fogfore.qrcodeauth.config.UserThreadLocal;
import com.fogfore.qrcodeauth.entity.RespBody;
import com.fogfore.qrcodeauth.entity.User;
import com.fogfore.qrcodeauth.service.RedisService;
import com.fogfore.qrcodeauth.utils.CommonUtils;
import com.fogfore.qrcodeauth.utils.QrcodeUtils;
import com.fogfore.qrcodeauth.utils.RedisUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/qrcode")
@LoginRequire
public class QrcodeController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserThreadLocal userThreadLocal;

    @RequestMapping("/get/credential")
    public void getCredential(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = userThreadLocal.get();

        String authCode = CommonUtils.getUUID();
        redisService.setEx(authCode, JSON.toJSONString(user), 1, TimeUnit.HOURS);

        BufferedImage image = QrcodeUtils.generateQrcode(authCode);
        RespBody.doImageResp(resp, image);
    }
}
