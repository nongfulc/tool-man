package cn.com.cworks.test;

import cn.com.cworks.fixedmsg.FixedLengthMsgUtil;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ToolManTest {

    @Test
    public void test1() throws UnsupportedEncodingException {

        Map dataMap = new HashMap();
        dataMap.put("Name", "鲁超");
        dataMap.put("Age", 28);
        dataMap.put("Gender", "M");
        dataMap.put("TransCode", "test");

        byte[] gbk = FixedLengthMsgUtil.pack(dataMap, "UTF-8");
        System.out.println(new String(gbk, "GBK"));

    }


    @Test
    public void test2() throws UnsupportedEncodingException {
//


    }
}
