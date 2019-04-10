package com.ruxuanwo.template.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

/**
 * DES加密解密工具类
 *
 * @Author: ruxuanwo
 */
public class CryptoUtil {

    private static Key DEFAULT_KEY;
    /**
     * 默认密钥
     */
    private static final String DEFAULT_SECRET_KEY = "hm";
    /**
     * 加密模式
     */
    private static final String DES = "DES";
    /**
     * 加密解密格式
     */
    private static final String FORMAT = "DES/ECB/PKCS5Padding";

    /**
     * 优先加载获得key
     */
    static {
        DEFAULT_KEY = obtainKey(DEFAULT_SECRET_KEY);
    }

    /**
     * 获得key
     **/
    private static Key obtainKey(String key) {
        //如果key等于null 使用默认密钥
        if (key == null) {
            return DEFAULT_KEY;
        }
        KeyGenerator generator = null;
        try {
            generator = KeyGenerator.getInstance(DES);
            //防止linux下 随机生成key
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes("UTF-8"));
            generator.init(secureRandom);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return generator.generateKey();
    }



    /**
     * null key 加密 使用默认密钥加密
     * String明文输入,String密文输出
     */
    public static String encode(String str) {
        return encode(null, str);
    }

    /**
     * 加密
     * String明文输入,String密文输出
     */
    public static String encode(String key, String str) {
        return Base64.encodeBase64URLSafeString(obtainEncode(key, str.getBytes()));
        // return Hex.encodeHexString(obtainEncode(key, str.getBytes()));
        // 可以转化为16进制数据
    }

    /**
     * null key 解密 使用默认密钥解密
     * 以String密文输入,String明文输出
     */
    public static String decode(String str) {
        return decode(null, str);
    }

    /**
     * 解密
     * 以String密文输入,String明文输出
     */
    public static String decode(String key, String str) {
        return new String(obtainDecode(key, Base64.decodeBase64(str)));
        // 可以转化为16进制的数据
//      try {
//          return new String(obtainDecode(key, Hex.decodeHex(str.toCharArray())));
//      } catch (DecoderException e) {
//          // TODO Auto-generated catch block
//          e.printStackTrace();
//      }
    }


    /**
     * 底层加密方法
     * 以byte[]明文输入,byte[]密文输出
     */
    private static byte[] obtainEncode(String key, byte[] str) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            Key key1 = obtainKey(key);
            cipher = Cipher.getInstance(FORMAT);
            cipher.init(Cipher.ENCRYPT_MODE, key1);
            byteFina = cipher.doFinal(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteFina;
    }

    /**
     * 底层解密方法
     * 以byte[]密文输入,以byte[]明文输出
     */
    private static byte[] obtainDecode(String key, byte[] str) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            Key key1 = obtainKey(key);
            cipher = Cipher.getInstance(FORMAT);
            cipher.init(Cipher.DECRYPT_MODE, key1);
            byteFina = cipher.doFinal(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteFina;
    }
}
