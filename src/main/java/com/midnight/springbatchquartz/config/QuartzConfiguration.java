package com.midnight.springbatchquartz.config;

import com.midnight.springbatchquartz.scheduler.ScheduledJob;
import com.midnight.springbatchquartz.scheduler.SchedulerJobFactory;
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
public class QuartzConfiguration {

    private final ApplicationContext applicationContext;

    private final DataSource dataSource;

    @Value("${application.scheduler.cron:0 */1 * ? * *}")
    private String cronTrigger;

    public QuartzConfiguration(ApplicationContext applicationContext, DataSource dataSource) {
        this.applicationContext = applicationContext;
        this.dataSource = dataSource;
    }

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
        triggerFactory.setCronExpression(cronTrigger);
        triggerFactory.setJobDetail(jobDetailBean());
        triggerFactory.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
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
