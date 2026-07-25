package com.monteiro.hexagonal_study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.monteiro.hexagonal_study.adapters.out.FindAddressByZipCodeAdapter;
import com.monteiro.hexagonal_study.adapters.out.FindCustomerByIdAdapter;
import com.monteiro.hexagonal_study.adapters.out.InsertCustomerAdapter;
import com.monteiro.hexagonal_study.application.core.usecase.FindCustomerByIdUseCase;
import com.monteiro.hexagonal_study.application.core.usecase.InsertCustomerUseCase;

@Configuration
public class FindCostumerByIdConfig {

    @Bean
    public FindCustomerByIdUseCase insertCustomerUserCase(
        FindCustomerByIdAdapter findCustomerByIdAdapter
        
    ){
        return new FindCustomerByIdUseCase(findCustomerByIdAdapter);
        
    }
}
