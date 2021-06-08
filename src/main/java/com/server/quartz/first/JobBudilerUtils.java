package com.server.quartz.first;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JobBudilerUtils {

    @Autowired
    private Scheduler scheduler;

   @Bean
    public void start() throws SchedulerException {
        JobDetail jobDetail = JobBuilder
                .newJob(FirstJob.class)
                .withIdentity("my job", "first job")
                .build();

        String triggerName = "TriggerName001";
        Trigger cronTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity("my trigger", "first Trigger")
                .withIdentity(new TriggerKey(triggerName))
                .withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?"))
                .build();

       boolean exists = scheduler.checkExists(new TriggerKey(triggerName));
       if(!exists){
           scheduler.scheduleJob(jobDetail, cronTrigger);
       }
       //nohup java -jar hyqeureka.jar > eureka.txt 2>&1 &
       scheduler.start();
    }
}
