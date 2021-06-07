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
    public void main() throws SchedulerException {
        JobDetail jobDetail = JobBuilder
                .newJob(FirstJob.class)
                .withIdentity("my job", "group1")
                .build();

        Trigger cronTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity("my trigger", "group1")
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5))
                .build();

        scheduler.start();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }
}
