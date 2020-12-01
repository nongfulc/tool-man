package cn.com.cworks.quartzlean;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestJob implements Job {
    /*
    Job：实现业务逻辑的接口；
    每次调度器执行Job时会创建示例；
    调用完成后对象被回收；
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LocalDateTime time = LocalDateTime.now();
        String timeStr = time.format(DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("[Quartz] Test Job is executing at " + timeStr);
        System.out.println(jobExecutionContext.getJobDetail().getDescription());
    }
}
