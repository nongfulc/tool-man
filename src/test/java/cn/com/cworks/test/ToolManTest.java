package cn.com.cworks.test;

import cn.com.cworks.tcptool.TCPTool;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ToolManTest {

    @Test
    public void test1() throws JsonProcessingException, UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>();
        map.put("transCode", "per.MCNetAcctDel");
        map.put("openUserId", "SIT777444");
        map.put("appCode", "WACAI");
        map.put("bankId", "730010");
        map.put("AccountNo", "6235670016503011646");
        map.put("CustomerName", "销户二");
        Map<String, Object> head = getHeadMap(map);
        Map<String, Object> sendMap = new HashMap<>();
        sendMap.put("Head", head);
        sendMap.put("Body", map);
        ObjectMapper objectMapper = new ObjectMapper();
        String send = objectMapper.writeValueAsString(sendMap);
        int length = send.getBytes(StandardCharsets.UTF_8).length;
        String len = String.valueOf(length);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8 - len.length(); i++) {
            sb.append(0);
        }
        sb.append(len);
        StringBuilder msg = sb.append(send);
        System.out.println(msg);
        String accept = TCPTool.send("localhost", 18001, msg.toString());
        System.out.println(accept);
    }


    private Map<String, Object> getHeadMap(Map<String, Object> map) {
        int i = 9876543;
        Map<String, Object> head = new HashMap<>();
        head.put("ChannelReqTimeStamp", System.currentTimeMillis());
        head.put("ChannelOrgJnlNo", i + (int) (Math.random() * 10000));
        head.put("ChannelServiceId", map.get("transCode"));
        head.put("ChannelCifId", map.get("openUserId"));
        head.put("ChannelMId", map.get("appCode"));
        head.put("ChannelBankId", map.get("bankId"));
        return head;
    }

}
