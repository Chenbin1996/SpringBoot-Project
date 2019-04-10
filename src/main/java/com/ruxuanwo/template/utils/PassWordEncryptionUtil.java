package com.ruxuanwo.template.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 用户密码加密类
 *
 * @author ruxuanwo
 */
@Slf4j
public class PassWordEncryptionUtil {
    private static String SYSTEM_USER_SECRETKEY="7744d91fb6c44c9dab4079b25a85fd47";
    /**
     * <pre>
     *     1. 秘钥 asdsada
     *     2. SHA256加密登录账号 加盐（秘钥）
     *     3. SHA512加密密码 加盐（登录账号加密结果）
     * <pre>
     * @author Yangxiaohui
     * @date 2018-11-2 12:03
     * @param loginName   登录账号
     * @param password      密码
     * @return String
     */
    public static String passWordEncryption(String loginName,String password){
        //手机号的盐
        String loginNamePassSalt = SYSTEM_USER_SECRETKEY;
        String result = null;
        //用户的盐
        try{
            String salt = get_SHA_256_SecurePassword(loginName, loginNamePassSalt);
            result = get_SHA_512_SecurePassword(password, salt);
        }catch (Exception e){
            log.error("生成密码失败！"+e.getMessage());
        }
        return result;
    }
    private static String securePassword(String passwordToHash, String salt, MessageDigest md){
        String encodeStr;
        md.update((passwordToHash+SYSTEM_USER_SECRETKEY+salt).getBytes());
        encodeStr = byte2Hex(md.digest());
        return encodeStr;
    }
    private static String get_SHA_1_SecurePassword(String passwordToHash, String salt) throws NoSuchAlgorithmException {
        return securePassword(passwordToHash,salt,MessageDigest.getInstance("SHA-1"));
    }
    private static String get_SHA_256_SecurePassword(String passwordToHash, String salt) throws NoSuchAlgorithmException{
        return securePassword(passwordToHash,salt,MessageDigest.getInstance("SHA-256"));
    }
    private static String get_SHA_384_SecurePassword(String passwordToHash, String salt) throws NoSuchAlgorithmException{
        return securePassword(passwordToHash,salt,MessageDigest.getInstance("SHA-384"));
    }
    private static String get_SHA_512_SecurePassword(String passwordToHash, String salt) throws NoSuchAlgorithmException{
        return securePassword(passwordToHash,salt,MessageDigest.getInstance("SHA-512"));
    }
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
