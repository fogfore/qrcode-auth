package com.fogfore.qrcodeauth.config;

import com.alibaba.fastjson.JSON;
import com.fogfore.qrcodeauth.annotation.LoginRequire;
import com.fogfore.qrcodeauth.entity.RespBody;
import com.fogfore.qrcodeauth.entity.User;
import com.fogfore.qrcodeauth.service.RedisService;
import com.fogfore.qrcodeauth.utils.RedisUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserThreadLocal userThreadLocal;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Object bean = handlerMethod.getBean();
            Method method = handlerMethod.getMethod();
            LoginRequire classAnnotation = bean.getClass().getAnnotation(LoginRequire.class);
            LoginRequire methodAnnotation = method.getAnnotation(LoginRequire.class);
            if (ObjectUtils.isNotEmpty(classAnnotation) || ObjectUtils.isNotEmpty(methodAnnotation)) {
                String skey = request.getHeader("skey");
                String value = redisService.get(RedisUtils.getSessionKey(skey));
                if (StringUtils.isEmpty(value)) {
                    RespBody.accessDeniedError("请登录");
                    return false;
                }
                userThreadLocal.set(JSON.parseObject(value, User.class));
            }
        }
        return true;
    }
}
