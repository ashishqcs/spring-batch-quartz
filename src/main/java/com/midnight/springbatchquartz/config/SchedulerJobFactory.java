package com.midnight.springbatchquartz.config;


import org.quartz.spi.TriggerFiredBundle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

public class SchedulerJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        super.setApplicationContext(applicationContext);
    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        return super.createJobInstance(bundle);
    }
}
