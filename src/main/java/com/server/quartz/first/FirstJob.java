package com.server.quartz.first;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component(value = "firstJob")
public class FirstJob implements Job {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Boolean persist = redisTemplate.persist("abc");
        System.out.println(persist);
        System.out.println("Hello quartz" + new Date());
    }


}
