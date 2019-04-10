package com.ruxuanwo.template.service.token.impl;


import com.ruxuanwo.template.constant.Constant;
import com.ruxuanwo.template.domain.SysUser;
import com.ruxuanwo.template.service.SysUserService;
import com.ruxuanwo.template.service.token.TokenService;
import com.ruxuanwo.template.utils.CookieUtil;
import com.ruxuanwo.template.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * token实现类，实现token相关服务
 *
 * @author ruxuanwo
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private SysUserService userService;

    @Override
    public SysUser getUserByRequest(HttpServletRequest request) {
        Map<String, Object> byRequest = parseTokenByRequest(request);
        SysUser user = userService.getById(byRequest.get("userId").toString());
        return user;
    }

    @Override
    public String getUserIdByRequest(HttpServletRequest request) {
        SysUser user = getUserByRequest(request);
        return user != null ? user.getId() : null;
    }

    @Override
    public String getTokenByRequest(HttpServletRequest request) {
        return getToken(request);
    }

    @Override
    public Map<String, Object> parseTokenByRequest(HttpServletRequest request) {
        return JWTUtil.getPayLoad(getToken(request));
    }

    @Override
    public String createToken(String userId) {
        return JWTUtil.createToken(userId);
    }

    @Override
    public String createToken(Map payload) {
        return JWTUtil.createToken(payload);
    }

    private String getToken(HttpServletRequest request){
        String token = CookieUtil.readCookie(request, Constant.HTTP_HEADER_ACCESS_TOKEN);
        return token;
    }
}
