package cn.com.cworks.fixedmsg;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

public class FixedLengthMsgUtil {
    private static final String CONFIG_ROOT_PATH = "/json/";
    private static final String OUT_CONFIG_PATH_SUFFIX = ".out.json";
    private static final String IN_CONFIG_PATH_SUFFIX = ".in.json";


    public static Map unPack(byte[] bytes, String charsetName, LinkedHashMap<String, Object> configMap) {
        Map<String, Object> dataMap = new HashMap<>();
        int length = Integer.parseInt(new String(bytes, 0, 6));
        final int[] index = {6};
        configMap.forEach((key, value) -> {
            try {
                if ("List".equals(key)) {
                    LinkedHashMap<String, Integer> listConfig = (LinkedHashMap) value;
                    int listLength = length - index[0] + 6;
                    int configLength = getLength(listConfig);
                    if (listLength % configLength != 0) {
                        throw new RuntimeException("报文好像有问题");
                    }
                    List list = new ArrayList();
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

    private static int getLength(LinkedHashMap<String, Integer> configMap) {
        final int[] i = {0};
        configMap.forEach((key, value) -> {
            i[0] += value;
        });
        return i[0];
    }

    /**
     * 获取发送的报文
     *
     * @param dataMap     包含所有发送信息的map
     * @param charsetName 发送信息的编码
     * @return 返回发送的字符串
     */
    public static byte[] pack(Map dataMap, String charsetName) {
        LinkedHashMap configMap = getConfig(CONFIG_ROOT_PATH + dataMap.get("TransCode") + OUT_CONFIG_PATH_SUFFIX);
        StringBuilder msg = new StringBuilder();
        configMap.forEach((key, length) -> {
            String sendValue = String.valueOf(dataMap.get(key));
            if (null == sendValue) {
                sendValue = "";
            }
            msg.append(autoFill(sendValue, (Integer) length));
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
     * 根据长度在左侧填充空格
     *
     * @param value  要填充的字符串
     * @param length 配置的长度
     * @return 填充后的值
     */
    private static String autoFill(String value, int length) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < length - value.length(); i++) {
            s.append(" ");
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
            int sLen = s.toString().getBytes(charsetName).length;
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
     * 从resource下读取配置文件内的json字符串，转成LinkedHashMap
     *
     * @param path resource下的路径，以"/"开头
     * @return 返回读取到配置信息的LinkedHashMap
     */
    public static LinkedHashMap getConfig(String path) {
        StringBuilder json = new StringBuilder();
        try (
                InputStream in = FixedLengthMsgUtil.class.getResourceAsStream(path);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        ) {

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
