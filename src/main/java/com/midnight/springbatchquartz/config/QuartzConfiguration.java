package com.midnight.springbatchquartz.config;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

@Configuration
@AllArgsConstructor
public class QuartzConfiguration {

    private final ApplicationContext applicationContext;

    private final DataSource dataSource;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerJobFactory schedulerJobFactory = new SchedulerJobFactory();
        schedulerJobFactory.setApplicationContext(applicationContext);

        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setConfigLocation(new ClassPathResource("quartz.properties"));
        factoryBean.setDataSource(dataSource);
        factoryBean.setJobFactory(schedulerJobFactory);

        return factoryBean;
    }
}
