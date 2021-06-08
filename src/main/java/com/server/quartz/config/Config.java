package com.server.quartz.config;

import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;

@Configuration
public class Config {

    @Autowired
    @Qualifier("druidDataSource")
    private DataSource druidDataSource;

    @Autowired
    private JobFactory jobFactory;

    //配置任务调度工厂,用来生成任务调度器,这是quartz的核心
    @Bean
    public SchedulerFactoryBean getSchedulerFactoryBean() throws IOException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobFactory); //在job可以进行SpringBean注入
        schedulerFactoryBean.setOverwriteExistingJobs(true); //开启更新job
        schedulerFactoryBean.setSchedulerName("Cluster_Scheduler"); //设置实例名称
        schedulerFactoryBean.setDataSource(druidDataSource);  //配置数据源,这是quartz使用的表的数据库存放位置
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext"); //设置实例在spring容器中的key
        schedulerFactoryBean.setTaskExecutor(schedulerThreadPool()); //配置线程池
        schedulerFactoryBean.setQuartzProperties(quartzProperties()); //配置配置文件
        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler scheduler() throws IOException {
        return getSchedulerFactoryBean().getScheduler();
    }


    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        // 在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }


    //线程池配置
    @Bean
    public Executor schedulerThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        return executor;
    }

}
