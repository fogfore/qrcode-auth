package com.fogfore.qrcodeauth.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Collection;
import java.util.UUID;

public class CommonUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static boolean isEmpty(JSONObject json) {
        Collection<Object> values = json.values();
        for (Object o : values) {
            if (ObjectUtils.isEmpty(o)) {
                return true;
            }
        }
        return false;
    }
}
