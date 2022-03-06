package com.midnight.springbatchquartz.processor;

import com.midnight.springbatchquartz.model.SourceProduct;
import com.midnight.springbatchquartz.model.TargetProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.OnProcessError;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Slf4j
@Component
@StepScope
public class ProductProcessor implements ItemProcessor<SourceProduct, TargetProduct> {

    @Override
    public TargetProduct process(SourceProduct product) throws Exception {

        return getTargetProduct(product);
    }

    private TargetProduct getTargetProduct(SourceProduct product) {

        return TargetProduct.builder()
                .code(product.getCode())
                .description(product.getDescription().toUpperCase(Locale.ROOT))
                .name(product.getName().toUpperCase(Locale.ROOT))
                .build();
    }

    @OnProcessError
    public void onProcessError(SourceProduct product, Exception exception) {
        log.error("Error while processing product : [{}], exception - {}", product.getName(), exception.getMessage());
    }
}
