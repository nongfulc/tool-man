package cn.com.cworks.datetime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

public class DateAndTimeUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 获取传入时间的当月第一天和最后一天
     *
     * @param time 指定的时间
     * @return 存储结果的Map，当月第一天key：MonthBegin，当月最后一天key：MonthEnd
     */
    public static Map<String, String> getMonthBeginAndEnd(LocalDateTime time) {
        Map<String, String> result = new HashMap<>();
        LocalDateTime firstDayOfMonth = time.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime lastDayOfMonth = time.with(TemporalAdjusters.lastDayOfMonth());
        result.put("MonthBegin", firstDayOfMonth.format(FORMATTER));
        result.put("MonthEnd", lastDayOfMonth.format(FORMATTER));
        return result;
    }


}
