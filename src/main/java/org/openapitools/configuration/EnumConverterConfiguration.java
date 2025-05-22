package org.openapitools.configuration;

import org.openapitools.model.TransactionType;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration(value = "org.openapitools.configuration.enumConverterConfiguration")
public class EnumConverterConfiguration {

    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.transactionTypeConverter")
    Converter<String, TransactionType> transactionTypeConverter() {
        return new Converter<String, TransactionType>() {
            @Override
            public TransactionType convert(String source) {
                return TransactionType.fromValue(source);
            }
        };
    }

}
