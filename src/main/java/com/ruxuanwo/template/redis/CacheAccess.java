package com.ruxuanwo.template.redis;

/**
 * 缓存接口，项目中使用缓存调用这个接口
 *
 * 有新的需求需要不同缓存在这个接口定义方法
 *
 * @author ruxuanwo
 */
public interface CacheAccess {

    /**
     * 将图形验证码放入缓存中
     *
     * @param imageCodeId
     * @param code
     * @param seconds
     * @throws Exception
     */
    void setImageCode(String imageCodeId, String code, int seconds)
            throws Exception;

    /**
     * 从缓存中取出验证码
     *
     * @param imageCodeId
     * @return
     * @throws Exception
     */
    String getImageCode(String imageCodeId) throws Exception;

    /**
     * 将短信验证码放入缓存中
     * @param messageCodeId
     * @param code
     * @param seconds
     * @throws Exception
     */
    void setMessageCode(String messageCodeId, String code, int seconds) throws Exception;

    /**
     * 从缓存中取出验证码
     * @param messageCodeId
     * @return
     * @throws Exception
     */
    String getMessageCode(String messageCodeId) throws Exception;
}
