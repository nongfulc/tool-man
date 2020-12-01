package cn.com.cworks.test;

import cn.com.cworks.TCPTool;
import org.junit.Test;


public class ToolManTest {

    @Test
    public void test1() {
        String accept = TCPTool.send("localhost", 18001, "");
        System.out.println(accept);
    }
}
