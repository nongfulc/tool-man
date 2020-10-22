package cn.com.cworks.test;

import cn.com.cworks.file.DateAndTimeUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;


public class FileUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(FileUtilsTest.class);

    @Test
    public void test1() {
        Map<String, String> map = DateAndTimeUtils.getMonthBeginAndEnd(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0).plusMonths(1));
        logger.info(map.toString());

    }
}
