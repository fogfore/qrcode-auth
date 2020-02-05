package com.fogfore.qrcodeauth.utils;

public class RedisUtils {
    private static final String APP_PREFIX = "/qrcodeauth";
    public static String getSessionKey(String skey) {
        return APP_PREFIX + "/skey/" + skey;
    }

    public static String getIdentityKey(String identity) {
        return APP_PREFIX + "/user/identity/" + identity;
    }
}
