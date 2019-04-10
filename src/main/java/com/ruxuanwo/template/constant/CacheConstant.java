package com.ruxuanwo.template.constant;

/**
 * 缓存常量类
 *
 * @author ruxuanwo
 */
public class CacheConstant {

    /**
     * 图形验证码
     */
    public final static String IMAGE_CODE = "image_code";
    /**
     * 短信验证码
     */
    public final static String MESSAGE_CODE = "message_code";
    /**
     * 下划线
     */
    public  static final String UNDERLINE_SEPARATOR = ":";
    /**
     * 图形验证码对应的key
     * 规则:"前缀:" + "image_code" + ":" + imageCodeId
     *
     * @param imageCodeId
     * @return
     */
    public static String getImageCodeKey(String imageCodeId) {
        return getPrefix() + IMAGE_CODE + UNDERLINE_SEPARATOR + imageCodeId;
    }

    /**
     * 短信验证码对应的key
     * 规则:"前缀:" + "message_code" + ":" + messageCodeId
     *
     * @param messageCodeId
     * @return
     */
    public static String getMessageCodeKey(String messageCodeId){
        return getPrefix() + MESSAGE_CODE + UNDERLINE_SEPARATOR + messageCodeId;
    }

    private static String getPrefix() {
        return "hm" + UNDERLINE_SEPARATOR;
    }
}
