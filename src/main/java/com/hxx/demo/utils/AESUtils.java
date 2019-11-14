package com.hxx.demo.utils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
/**
 * @author Hxx
 */
public class AESUtils {
    /**
     * @Author Hxx
     * @Description //TODO 加密
     * @Date 16:00 2019/10/28
     * @Param
     * @return
     **/
    public static String AESEncrypt(String content, String key) throws Exception {
        if (key == null) {
            System.out.print("Key不能为空null");
            return null;
        }
        if (key.length() != 16) {
            System.out.print("Key的长度不是16位");
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"));
        byte[] bytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        return new BASE64Encoder().encode(bytes);
    }

    /**
     * @Author Hxx
     * @Description //TODO 解密
     * @Date 16:00 2019/10/28
     * @Param
     * @return
     **/
    public static String AESDecrypt(String content, String key) throws Exception {
        if (key == null) {
            System.out.print("Key不能为空null");
            return null;
        }
        if (key.length() != 16) {
            System.out.print("Key的长度不是16位");
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"));
        byte[] bytes = new BASE64Decoder().decodeBuffer(content);
        bytes = cipher.doFinal(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }


}