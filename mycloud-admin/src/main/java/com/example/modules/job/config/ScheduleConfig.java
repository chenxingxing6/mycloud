package com.example.modules.job.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 定时任务配置
 */
@Configuration
public class ScheduleConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //factory.setDataSource(dataSource);

        //quartz参数
        Properties prop = new Properties();
        prop.put("org.quartz.scheduler.instanceName", "DiskScheduler");
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        //线程池配置
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "20");
        prop.put("org.quartz.threadPool.threadPriority", "5");
        //JobStore配置
        prop.put("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
        prop.put("org.quartz.jobStore.misfireThreshold", "12000");
        //集群配置
        //prop.put("org.quartz.jobStore.isClustered", "true");
        //prop.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
        //prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");

        //prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        //prop.put("org.quartz.jobStore.selectWithLockSQL", "SELECT * FROM {0}LOCKS UPDLOCK WHERE LOCK_NAME = ?");

        factory.setQuartzProperties(prop);
        factory.setSchedulerName("DiskScheduler");
        //延时启动
        factory.setStartupDelay(30);
        //设置自动启动，默认为true
        factory.setAutoStartup(true);
        return factory;
    }
}
