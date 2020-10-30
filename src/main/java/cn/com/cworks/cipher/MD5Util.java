package cn.com.cworks.cipher;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    private static final String MD5_SALT = "5Liq5raI5oGv4pid77iP";

    /**
     * 获取md5签名后的16进制字符串
     *
     * @param msg         需要签名的字符串
     * @param charsetName 字符编码
     * @return 签名后的16进制字符串
     */
    public static String md5Encode(String msg, String charsetName) {
        try {
            return md5Encode((msg + MD5_SALT).getBytes(charsetName));
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

    public static void main(String[] args) {
        String s = md5Encode("hehe", "UTF-8");
        System.out.println(s);
        System.out.println(Base64Util.encryptBASE64(s, "UTF-8"));

    }
}
