package com.midnight.springbatchquartz.config;

import com.midnight.springbatchquartz.listener.ManualJobListener;
import com.midnight.springbatchquartz.listener.ScheduledJobListener;
import com.midnight.springbatchquartz.model.SourceProduct;
import com.midnight.springbatchquartz.model.TargetProduct;
import com.midnight.springbatchquartz.processor.ProductProcessor;
import com.midnight.springbatchquartz.reader.ProductsReader;
import com.midnight.springbatchquartz.writer.ProductWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean(name = "manualBatchJob")
    public Job manualBatchJob(@Qualifier("manualBatchJobStep") Step manualStep, ManualJobListener listener) {

        return jobBuilderFactory.get("manualJob")
                .listener(listener)
                .incrementer(new RunIdIncrementer())
                .flow(manualStep)
                .end()
                .build();

    }

    @Bean(name = "manualBatchJobStep")
    public Step manualBatchJobStep(ProductsReader reader, ProductProcessor processor, ProductWriter writer) {
        return stepBuilderFactory.get("manualStep")
                .<SourceProduct, TargetProduct>chunk(2)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skipLimit(2)
                .skip(Exception.class)
                .build();

    }

    @Bean(name = "scheduledBatchJob")
    public Job scheduledBatchJob(@Qualifier("scheduledBatchStep") Step manualStep, ScheduledJobListener listener) {

        return jobBuilderFactory.get("scheduledBatchJob")
                .listener(listener)
                .incrementer(new RunIdIncrementer())
                .flow(manualStep)
                .end()
                .build();

    }

    @Bean(name = "scheduledBatchStep")
    public Step scheduledBatchStep(ProductsReader reader, ProductProcessor processor, ProductWriter writer) {
        return stepBuilderFactory.get("scheduledBatchStep")
                .<SourceProduct, TargetProduct>chunk(2)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skipLimit(2)
                .skip(Exception.class)
                .build();
    }

}
