package com.ruxuanwo.template.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie操作类
 *
 * @author ruxuanwo
 */
public class CookieUtil {

    /**
     * <pre>
     *     响应写入Cookie
     * <pre>
     * @author Yangxiaohui
     * @date 2019-3-20 11:32
     * @param key       key
     * @param value     值
     */
    public static void writeCookie(HttpServletResponse response, String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(30 * 24 * 60 * 60);/* 有效期  一个月 */
        cookie.setPath("/");/* 只要在该域下，都能访问 */
        response.addCookie(cookie);
    }

    /**
     * <pre>
     *     读取cookie
     * <pre>
     * @author Yangxiaohui
     * @date 2019-3-20 11:34
     * @param key   key
     * @return String
     */
    public static String readCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(key)) {
                return cookie.getValue();
            }
        }
        return null;
    }


    /**
     * <pre>
     *     删除Cookie
     * <pre>
     * @author Yangxiaohui
     * @date 2019-3-20 11:37
     * @param key key
     */
    public static void removeCookie(HttpServletResponse response, String key) {
        Cookie cookie = new Cookie(key, "");
        cookie.setPath("/");
        //将cookie的有效期设置为0，命令浏览器删除该cookie
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
