package com.midnight.springbatchquartz.writer;

import com.midnight.springbatchquartz.model.TargetProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.OnWriteError;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Component
@StepScope
public class ProductWriter extends JpaItemWriter<TargetProduct> {

    public ProductWriter(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }

    @OnWriteError
    public void onWriteError(TargetProduct product, Exception exception) {
        log.error("Error while writing product : [{}], exception - {}", product.getName(), exception.getMessage());
    }
}
