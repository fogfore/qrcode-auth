package com.fogfore.qrcodeauth.entity;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class RespBody {
    public static final String OK_STATUS = "200";
    public static final String ERROR_STATUS = "500";
    public static final String ARGS_ERROR_STATUS = "501";
    public static final String ACCESS_DENIED_STATUS = "502";
    public static final String EXCEPTION_ERROR_STATUS = "503";

    private String status;
    private String msg;
    private Object data;

    public static RespBody ok(Object data) {
        return new RespBody(OK_STATUS, null, data);
    }

    public static RespBody ok(String msg) {
        return new RespBody(OK_STATUS, msg, null);
    }

    public static RespBody argsError(String msg) {
        return new RespBody(ARGS_ERROR_STATUS, msg, null);
    }

    public static RespBody accessDeniedError(String msg) {
        return new RespBody(ACCESS_DENIED_STATUS, msg, null);
    }

    public static RespBody exceptionError(String msg) {
        return new RespBody(EXCEPTION_ERROR_STATUS, msg, null);
    }

    public void doResp(HttpServletResponse resp) throws IOException {
        // 允许跨域
        resp.setHeader("Access-Control-Allow-Origin", "*");
        // 允许自定义请求头token(允许head跨域)
        resp.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        resp.setHeader("Content-Type", "application/json;charset=utf8");
        resp.getWriter().write(JSON.toJSONString(this));
    }

    public static void doImageResp(HttpServletResponse resp, BufferedImage image) throws IOException {
        resp.setHeader("Content-Type", "image/png");
        ServletOutputStream out = resp.getOutputStream();
        ImageIO.write(image, "png", out);
        out.flush();
        out.close();
    }
}
