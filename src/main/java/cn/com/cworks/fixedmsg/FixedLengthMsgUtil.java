package cn.com.cworks.fixedmsg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("all")
public class FixedLengthMsgUtil {

    private static final String CONFIG_ROOT_PATH = "/paymsg/";
    private static final String IN_CONFIG_PATH_SUFFIX = ".response.json";
    private static final String OUT_CONFIG_PATH_SUFFIX = ".request.json";

    /**
     * 获取发送的报文的字符数组
     *
     * @param dataMap     包含所有发送信息的map
     * @param charsetName 发送信息的编码
     * @return 返回发送的字符串
     */
    public static byte[] pack(Map dataMap, String charsetName) {
        LinkedHashMap configMap = getConfig(
                CONFIG_ROOT_PATH + dataMap.get("HostTransactionCode") + OUT_CONFIG_PATH_SUFFIX);
        StringBuilder msg = new StringBuilder();
        configMap.forEach((key, length) -> {
            String sendValue = String.valueOf(dataMap.get(key));
            if (null == sendValue) {
                sendValue = "";
            }
            msg.append(autoFill(sendValue, (Integer) length, charsetName));
        });
        String length = getHeadLength(msg, charsetName);
        msg.insert(0, length);
        try {
            return msg.toString().getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持的编码格式");
        }
    }

    /**
     * 将返回的字符串组封装成map
     *
     * @param bytes       获取到的报文字符数组
     * @param charsetName 字符串编码
     * @param length      返回报文的报文体长度（含报文头长度）
     * @param map         存储请求数据的map
     * @return 返回封装结果后的map
     */
    public static Map unPack(byte[] bytes, String charsetName, int length, Map map) {
        LinkedHashMap<String, Object> configMap = getConfig(
                CONFIG_ROOT_PATH + map.get("HostTransactionCode") + IN_CONFIG_PATH_SUFFIX);
        Map<String, Object> dataMap = new HashMap<>();
        final int[] index = {0};
        configMap.forEach((key, value) -> {
            try {
                if ("List".equals(key)) {
                    LinkedHashMap<String, Integer> listConfig = (LinkedHashMap) value;
                    int listLength = length - 6 - index[0];
                    int configLength = getLength(listConfig);
                    if (listLength % configLength != 0) {
                        throw new RuntimeException("报文好像有问题");
                    }
                    List list = new ArrayList<>();
                    for (int i = 0; i < listLength / configLength; i++) {
                        Map temp = new HashMap();
                        listConfig.forEach((childKey, childValue) -> {
                            try {
                                temp.put(childKey, new String(bytes, index[0], childValue, charsetName).trim());
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            index[0] += childValue;
                        });
                        list.add(temp);
                    }
                    dataMap.put("List", list);
                } else {
                    dataMap.put(key, new String(bytes, index[0], (int) value, charsetName).trim());
                    index[0] += (int) value;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        });
        return dataMap;

    }

    /**
     * 获取configMap内配置的字段长度之和
     *
     * @param configMap 配置map
     * @return configMap内配置的字段长度之和
     */
    private static int getLength(LinkedHashMap<String, Integer> configMap) {
        final int[] i = {0};
        configMap.forEach((key, value) -> {
            i[0] += value;
        });
        return i[0];
    }

    /**
     * 根据长度在左侧填充空格
     *
     * @param value  要填充的字符串
     * @param length 配置的长度
     * @return 填充后的值
     */
    private static String autoFill(String value, int length, String charsetName) {
        StringBuilder s = new StringBuilder();
        try {
            for (int i = 0; i < length - value.getBytes(charsetName).length; i++) {
                s.append(" ");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        s.append(value);
        return s.toString();
    }

    /**
     * 获取表示信息长度的头部字符串，头长不计入消息长度
     *
     * @param s           发送的消息内容
     * @param charsetName 消息的字符编码
     * @return 返回头部信息
     */
    private static String getHeadLength(StringBuilder s, String charsetName) {
        StringBuilder head = new StringBuilder();
        try {
            int sLen = s.toString().getBytes(charsetName).length + 4;
            int length = String.valueOf(sLen).length();
            if (length < 4) {
                for (int i = 0; i < 4 - length; i++) {
                    head.append("0");
                }
            }
            head.append(sLen);
            return head.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持的编码格式！");
        }
    }

    /**
     * 根据路径获取配置内容
     *
     * @param path 配置文件路径
     * @return 返回配置信息map
     */
    private static LinkedHashMap getConfig(String path) {
        StringBuilder json = new StringBuilder();
        try (InputStream in = FixedLengthMsgUtil.class.getResourceAsStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {

            String line;
            while (null != (line = reader.readLine())) {
                json.append(line);
            }
            return new ObjectMapper().readValue(json.toString(), LinkedHashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
