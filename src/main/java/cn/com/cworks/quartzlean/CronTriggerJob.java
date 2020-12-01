package cn.com.cworks.quartzlean;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class CronTriggerJob {

    public static void main(String[] args) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(TestJob.class)
                .withDescription("Jon Description...")
                .withIdentity("TestJob", "Group1").build();
        Trigger testTrigger = TriggerBuilder.newTrigger()
                .withIdentity("TestTrigger", "TriggerGroup1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .withDescription("TestTrigger description..")
                .build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, testTrigger);

    }
}
