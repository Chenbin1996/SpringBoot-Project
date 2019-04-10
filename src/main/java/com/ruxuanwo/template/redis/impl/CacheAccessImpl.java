package com.ruxuanwo.template.redis.impl;


import com.ruxuanwo.template.constant.CacheConstant;
import com.ruxuanwo.template.redis.CacheAccess;
import com.ruxuanwo.template.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 缓存实现类
 *
 * @author ruxuanwo
 */
@Component("cacheAccess")
public class CacheAccessImpl implements CacheAccess {


    @Autowired
    private RedisClient redisClient;

    /**
     * 删除时，设置过期时间，3秒
     */
    private static int DELETE_TIME = 3;


    @Override
    public void setImageCode(String imageCodeId, String code, int seconds) throws Exception {
        redisClient.set(CacheConstant.getImageCodeKey(imageCodeId), code, seconds);
    }

    @Override
    public String getImageCode(String imageCodeId) throws Exception {
        return redisClient.get(CacheConstant.getImageCodeKey(imageCodeId));
    }

    @Override
    public void setMessageCode(String messageCodeId, String code, int seconds) throws Exception {
        redisClient.set(CacheConstant.getMessageCodeKey(messageCodeId), code, seconds);
    }

    @Override
    public String getMessageCode(String messageCodeId) throws Exception {
        return redisClient.get(CacheConstant.getMessageCodeKey(messageCodeId));
    }


}
