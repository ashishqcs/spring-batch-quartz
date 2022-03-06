package com.midnight.springbatchquartz.reader;

import com.midnight.springbatchquartz.model.SourceProduct;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;


@Component
@StepScope
public class ProductsReader extends JpaPagingItemReader<SourceProduct> {

    private static final String SQL_GET_ALL_PRODUCTS = "select * from ProductEntity;";

    public ProductsReader(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
        setPageSize(10);
        setQueryString(SQL_GET_ALL_PRODUCTS);
    }
}
