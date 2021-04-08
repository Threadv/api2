package com.ifenghui.storybookapi.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.sql.DataSource;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
public class ScheduleConfig {
    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(dataSource);
    }


//    @Override
//    public void configureTasks(ScheduledTaskRegistrar taskRegistrar)
//    {
//        taskRegistrar.setScheduler(taskExecutor());
//    }
//
//
//    @Bean(destroyMethod="shutdown")
//     public Executor taskExecutor() {
//         return Executors.newScheduledThreadPool(20);
//    }
}
