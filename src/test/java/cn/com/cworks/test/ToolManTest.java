package cn.com.cworks.test;


import org.junit.Test;

import java.io.*;
import java.util.UUID;

public class ToolManTest {
    @Test
    public void test01() throws IOException {

        File file = new File("/Users/lc_666/Desktop/test/test.png");
        File parentFile = file.getParentFile();
        System.out.println(parentFile);
        if (!parentFile.exists()) {
            boolean mkdirs = parentFile.mkdirs();
            System.out.println(mkdirs);
        }

    }

    public static void byteArrayToFile(byte[] src, String filePath) {
        File dest = new File(filePath);
        ByteArrayInputStream is = new ByteArrayInputStream(src);
        OutputStream os = null;
        try {
            os = new FileOutputStream(dest);
            byte[] flush = new byte[5];
            int len = -1;
            while ((len = is.read(flush)) != -1) {
                os.write(flush, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public static String saveImage(byte[] src, String filePath) {
        File dest = new File(filePath);
        ByteArrayInputStream in = new ByteArrayInputStream(src);
        try (FileOutputStream out = new FileOutputStream(dest)) {
            byte[] flush = new byte[1024];
            int len = -1;
            while ((len = in.read(flush)) != -1) {
                out.write(flush, 0, len);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }


    @Test
    public void test02() throws IOException {

        String s = "http://app.test.jzzdtech.cn:19001/images/INDEXSLIDE/20210603/1622685424376.jpg";

        String ss = s.replace("http://app.test.jzzdtech.cn:19001/images/", "sdfsdf");
        System.out.println(ss);
    }

    @Test
    public void test03() {

        System.out.println(UUID.randomUUID().toString().replaceAll("-", "").length());
    }


}