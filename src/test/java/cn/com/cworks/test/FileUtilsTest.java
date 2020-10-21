package cn.com.cworks.test;

import cn.com.cworks.file.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(FileUtilsTest.class);

    @Test
    public void test1() {
        String info = FileUtils.getInfoWithLineNumber("/Users/lc_666/Desktop/xml/667300030542600010.txt", 13, "GBK");
        String dataFileName = info.substring(info.indexOf("e_"), info.lastIndexOf("</"));
        logger.debug(dataFileName);
        String data = FileUtils.getInfoWithLineNumber("/Users/lc_666/Desktop/data/06/" + dataFileName, 0, "GBK");
        System.out.println(data);
    }
}
