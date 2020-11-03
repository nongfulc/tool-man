package cn.com.cworks.cipher;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {


    /**
     * 对字符串进行md5签名
     *
     * @param msg 要签名的字符串
     * @return 签名后的数据
     */
    public static String md5Encode(String msg) {
        return md5Encode(msg.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 获取md5签名后的16进制字符串
     *
     * @param msg         需要签名的字符串
     * @param charsetName 字符编码
     * @return 签名后的16进制字符串
     */
    public static String md5Encode(String msg, String charsetName) {
        try {
            return md5Encode(msg.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持的编码格式！");
        }
    }

    /**
     * md5对字节数组进行签名
     *
     * @param source 需要签名的字节数组
     * @return 返回签名后的16进制字符串
     */
    private static String md5Encode(byte[] source) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(source);
            byte[] bytes = digest.digest();
            return HexUtil.encodeBytesToString(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No such Algorithm.");
        }
    }
}
