package com.monteiro.hexagonal_study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.monteiro.hexagonal_study.adapters.out.FindAddressByZipCodeAdapter;
import com.monteiro.hexagonal_study.adapters.out.InsertCustomerAdapter;
import com.monteiro.hexagonal_study.application.core.usecase.InsertCustomerUseCase;

@Configuration
public class InsertCostumerConfig {

    @Bean
    public InsertCustomerUseCase insertCustomerUserCase(
        FindAddressByZipCodeAdapter findAddressByZipCodeAdapter,
        InsertCustomerAdapter insertCustomerAdapter
        
    ){
        return new InsertCustomerUseCase(findAddressByZipCodeAdapter, insertCustomerAdapter);
        
    }
}
