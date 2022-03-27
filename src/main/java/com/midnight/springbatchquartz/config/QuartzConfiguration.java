package com.midnight.springbatchquartz.config;

import com.midnight.springbatchquartz.scheduler.ScheduledJob;
import com.midnight.springbatchquartz.scheduler.SchedulerJobFactory;
import lombok.AllArgsConstructor;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.text.ParseException;

@Configuration
@AllArgsConstructor
public class QuartzConfiguration {

    private final ApplicationContext applicationContext;

    private final DataSource dataSource;

    @Value("${application.scheduler.cron: */50 * * * * ? *}")
    private String cronTrigger;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws ParseException {
        SchedulerJobFactory schedulerJobFactory = new SchedulerJobFactory();
        schedulerJobFactory.setApplicationContext(applicationContext);

        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
        schedulerFactory.setDataSource(dataSource);
        schedulerFactory.setJobFactory(schedulerJobFactory);
        schedulerFactory.setTriggers(cronTriggerBean());

        return schedulerFactory;
    }

    private CronTrigger cronTriggerBean() throws ParseException {
        CronTriggerFactoryBean triggerFactory = new CronTriggerFactoryBean();
        triggerFactory.setName("cronTrigger");
        triggerFactory.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
        triggerFactory.setCronExpression(cronTrigger);
        triggerFactory.setJobDetail(jobDetailBean());
        triggerFactory.afterPropertiesSet();
        return triggerFactory.getObject();
    }

    private JobDetail jobDetailBean() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(ScheduledJob.class);
        jobDetailFactory.setName("jobDetail");
        jobDetailFactory.setDurability(false);
        jobDetailFactory.afterPropertiesSet();
        return jobDetailFactory.getObject();
    }
}
