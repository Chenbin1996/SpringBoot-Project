package com.ruxuanwo.template.service.token;


import com.ruxuanwo.template.domain.SysUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * token的相关服务，在这里根据request获取用户信息
 *
 * @author ruxuanwo
 */
public interface TokenService {
    /**
     * 获取请求中的token，解析出用户信息
     *
     * @param request
     * @return
     */
    SysUser getUserByRequest(HttpServletRequest request);

    /**
     * 获取请求中的token，解析出用户ID
     *
     * @param request
     * @return
     */
    String getUserIdByRequest(HttpServletRequest request);

    /**
     * 获取请求中的token
     *
     * @param request
     * @return
     */
    String getTokenByRequest(HttpServletRequest request);

    /**
     * 获取请求中的token，解析token的载体
     *
     * @param request
     * @return
     */
    Map<String, Object> parseTokenByRequest(HttpServletRequest request);

    /**
     * 创建token，将userId作为载体
     *
     * @param userId
     * @return
     */
    String createToken(String userId);

    /**
     * 创建token，自定义载体内容，如：{userId, "123"}
     *
     * @param payload
     * @return
     */
    String createToken(Map payload);
}
