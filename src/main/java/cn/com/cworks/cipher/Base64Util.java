package cn.com.cworks.cipher;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Util {

    /**
     * 使用基本解码方式对base64字符串进行解码，UTF-8
     *
     * @param base64 要解码的base64字符串
     * @return 解码后的字符串
     */
    public static String decryptBASE64(String base64) {
        return decryptBASE64(base64, StandardCharsets.UTF_8.name());
    }

    /**
     * 使用基本解码方式对base64字符串进行解码
     *
     * @param base64      要解码的base64字符串
     * @param charsetName 字符串编码
     * @return 解码后的字符串
     */
    public static String decryptBASE64(String base64, String charsetName) {
        try {
            if (null == charsetName || "".equals(charsetName))
                charsetName = StandardCharsets.UTF_8.name();
            byte[] strBytes = base64.getBytes();
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decode = decoder.decode(strBytes);
            return new String(decode, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported CHARSET NAME-不支持的编码格式");
        }
    }

    /**
     * Base64 编码，UTF-8
     *
     * @param msg 要编码的字符串
     * @return 返回编码后的字符串
     */
    public static String encryptBASE64(String msg) {
        return encryptBASE64(msg, StandardCharsets.UTF_8.name());
    }

    /**
     * Base64 编码指定字符串，基本编码方式
     *
     * @param msg         要编码的字符串
     * @param charsetName 字符串编码
     * @return 返回编码后的字符串
     */
    public static String encryptBASE64(String msg, String charsetName) {
        try {
            if (null == charsetName || "".equals(charsetName))
                charsetName = StandardCharsets.UTF_8.name();
            byte[] strBytes = msg.getBytes(charsetName);
            Base64.Encoder encoder = Base64.getEncoder();
            return (encoder.encodeToString(strBytes));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported CHARSET NAME-不支持的编码格式");
        }

    }

}
