package cn.com.cworks.cipher;


public class HexUtil {

    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};


    /**
     * 将字节数组转换成16字符串，可选择大小写
     *
     * @param src         需要转换的字节数组
     * @param toLowerCase 是否小写
     * @return 返回转换后的16进制字符串
     */
    public static String encodeBytesToString(byte[] src, boolean toLowerCase) {
        return new String(encodeBytesToHex(src, toLowerCase));
    }

    /**
     * 将字节数组转换成16进制小写字符串
     *
     * @param src 需要转换的字节数组
     * @return 转换后的16进制小写字符串
     */
    public static String encodeBytesToString(byte[] src) {
        return new String(encodeBytesToHex(src));
    }

    /**
     * 将字节数组转换成16进制字符数组，可选择大小写
     *
     * @param src         需要转换的字符串
     * @param toLowerCase 是否小写
     * @return 转换后的16进制字符数组
     */
    private static char[] encodeBytesToHex(byte[] src, boolean toLowerCase) {
        return encodeBytesToHex(src, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * 将字节数组转换成16进制小写字符数组
     *
     * @param src 需要转换的字符串
     * @return 转换后的小写16进制字符数组
     */
    private static char[] encodeBytesToHex(byte[] src) {
        return encodeBytesToHex(src, true);
    }

    /**
     * 将字节转换为16进制字符数组
     *
     * @param src    需要转换的字节
     * @param digits 匹配的字符数组，可选择大写或者小写
     * @return 返回表示字节的16进制字符数组
     */
    private static char[] encodeBytesToHex(byte[] src, char[] digits) {
        char[] des = new char[src.length << 1];
        for (int i = 0, j = 0; i < src.length; i++) {
            des[j++] = digits[(0xF0 & src[i]) >>> 4];
            des[j++] = digits[0x0F & src[i]];
        }
        return des;
    }

}
