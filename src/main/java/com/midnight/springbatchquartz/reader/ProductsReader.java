package com.midnight.springbatchquartz.reader;

import com.midnight.springbatchquartz.model.SourceProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.batch.item.database.orm.JpaQueryProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Component
@StepScope
public class ProductsReader extends JpaPagingItemReader<SourceProduct> {

    private static final String SQL_GET_ALL_PRODUCTS = "select * from source_product";

    public ProductsReader(EntityManagerFactory entityManagerFactory,
                          @Value("#{stepExecution.jobExecution.executionContext}") ExecutionContext executionContext) {
        setEntityManagerFactory(entityManagerFactory);
        setPageSize(10);
        setQueryProvider(queryProvider());
        setQueryString(SQL_GET_ALL_PRODUCTS);
        log.info("Parameter from execution context {}", executionContext.get("beforeJobParam"));
    }

    private JpaQueryProvider queryProvider(){

        JpaNativeQueryProvider queryProvider = new JpaNativeQueryProvider();
        queryProvider.setSqlQuery(SQL_GET_ALL_PRODUCTS);
        queryProvider.setEntityClass(SourceProduct.class);

        return queryProvider;
    }
}
