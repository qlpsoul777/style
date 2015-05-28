package com.qlp.utils;

import org.apache.shiro.SecurityUtils;

/**
 * Created by july on 2015/5/27.
 */
public class SecurityUtil {

    public static String getCurrentUserLoginName() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }
}
