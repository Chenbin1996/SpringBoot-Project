package com.ruxuanwo.template.utils;

import com.alibaba.fastjson.JSONObject;
import com.ruxuanwo.template.constant.Constant;
import com.ruxuanwo.template.exception.NotLoginException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Token相关工具类
 *
 * @author ruxuanwo
 */

public class JWTUtil {

    private static String secret = "hmwh";

    /**
     * 生成token，自定义载体内容（角色等），过期时间， 用户Id作为主题
     * @param payload 载体
     * @param expiration 过期时间，单位是毫秒
     * @param userId 用户ID
     * @return
     */
    public static String createToken(Map payload, Long expiration, String userId){
        String token = Jwts.builder()
                .setClaims(payload)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .setSubject(userId)
                .compact();
        return token;
    }

    /**
     * 永不过期的token，自定义载体内容（角色等）， 用户Id作为主题
     * @param payload 载体
     * @param userId 用户ID
     * @return
     */
    public static String createToken(Map payload, String userId){
        String token = Jwts.builder()
                .setClaims(payload)
                .signWith(SignatureAlgorithm.HS256, secret)
                .setSubject(userId)
                .compact();
        return token;
    }

    /**
     * 生成token，自定义载体内容（角色，用户ID等）， 过期时间
     * @param payload 载体
     * @param expiration 过期时间
     * @return
     */
    public static String createToken(Map payload, Long expiration){
        String token = Jwts.builder()
                .setClaims(payload)
                .signWith(SignatureAlgorithm.HS256, secret)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
        return token;
    }

    /**
     * 永不过期的token，自定义载体内容（角色，用户ID等）
     * @param payload 载体
     * @return
     */
    public static String createToken(Map payload){
        String token = Jwts.builder()
                .setClaims(payload)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return token;
    }

    /**
     * 永不过期的token，将用户ID作为载体
     * @param userId 载体
     * @return
     */
    public static String createToken(String userId){
        Map payload = new HashMap(16);
        payload.put("userId", userId);
        String token = Jwts.builder()
                .setClaims(payload)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return token;
    }

    /**
     * 解密，返回payload
     *
     * @param token
     * @return
     */
    public static Claims getPayLoad(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            throw new NotLoginException(Constant.ACCESS_TOKEN_ILLEGAL);
        }
        return claims;
    }

    /**
     * 解密，返回subject
     *
     * @param token
     * @return
     */
    public static String getSubject(String token) {
        return getPayLoad(token).getSubject();
    }

    /**
     * 解密，返回header
     *
     * @param token
     * @return
     */
    public static JwsHeader getHeader(String token){
        JwsHeader header = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getHeader();
        return header;
    }

    /**
     * 解析token，返回payload，即token包含的信息，形如:{"userId":"12345"}
     * 注意：该方法只解析token的有效性，不校验token的有效性！，区别于getPayLoad(String token)
     *
     * @param token
     * @return
     */
    public static Map<String, Object> parseToken(String token) {
        if (token == null) {
            return null;
        }
        String[] str = token.split("\\.");
        if (str.length != 3) {
            return null;
        }
        return JSONObject.parseObject(EncryptUtil.decodeBase64(str[1]));
    }

    /**
     * 效验token是否过期
     * @param token
     * @return
     */
    public static boolean isExpiration(String token){
        return getPayLoad(token).getExpiration().before(new Date());
    }

}
