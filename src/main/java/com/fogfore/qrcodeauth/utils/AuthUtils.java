package com.fogfore.qrcodeauth.utils;

/**
 * @author: 岳英豪
 */
public class AuthUtils {
    public static final String VISITOR_AUTH = "1";
    public static final String ADMIN_AUTH = "2";
    public static final String NO_AUTH = "0";

    public static boolean isPermitVisit(String userAuth) {
        if (VISITOR_AUTH.equals(userAuth) || ADMIN_AUTH.equals(userAuth)) {
            return true;
        }
        return false;
    }

    public static boolean isAdmin(String userAuth) {
        return ADMIN_AUTH.equals(userAuth);
    }
}
