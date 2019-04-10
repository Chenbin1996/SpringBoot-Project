package com.ruxuanwo.template.interceptor;


import com.ruxuanwo.template.constant.Constant;
import com.ruxuanwo.template.domain.SysUser;
import com.ruxuanwo.template.exception.NotLoginException;
import com.ruxuanwo.template.service.SysUserService;
import com.ruxuanwo.template.utils.CookieUtil;
import com.ruxuanwo.template.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对任何请求进行token鉴权
 * <p>
 * 除去登录和获取验证码接口
 *
 * @author ruxuanwo
 */
public class ApiInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SysUserService userService;

    /**
     * 任何请求前进行拦截，效验是否有token
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String token = CookieUtil.readCookie(request, Constant.HTTP_HEADER_ACCESS_TOKEN);

        if (token == null) {
            sendRedirect(response);
            throw new NotLoginException();
        }
        Claims payLoad = JWTUtil.getPayLoad(token);
        SysUser user = userService.getById(payLoad.get("userId").toString());
        if (user == null) {
            sendRedirect(response);
            throw new NotLoginException(Constant.ACCESS_TOKEN_INVALID);
        }
        return true;
    }

    /**
     * 重定向到登录页面接口
     *
     * @param response
     * @throws Exception
     */
    private void sendRedirect(HttpServletResponse response) throws Exception {
        response.sendRedirect("/login/loginUI");
    }

}
