package com.monteiro.hexagonal_study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.monteiro.hexagonal_study.adapters.out.FindAddressByZipCodeAdapter;
import com.monteiro.hexagonal_study.adapters.out.FindCustomerByIdAdapter;
import com.monteiro.hexagonal_study.adapters.out.UpdateCustomerAdapter;
import com.monteiro.hexagonal_study.application.core.usecase.FindCustomerByIdUseCase;
import com.monteiro.hexagonal_study.application.core.usecase.UpdateCustomerUseCase;

@Configuration
public class UpdateCustomerConfig {

    @Bean
    public UpdateCustomerUseCase updateCustomerUserCase(
        FindCustomerByIdUseCase findCustomerByIdUseCase,
        FindAddressByZipCodeAdapter findAddressByZipCodeAdapter,
        UpdateCustomerAdapter updateCustomerAdapter    
    ){
        return new UpdateCustomerUseCase(findCustomerByIdUseCase, findAddressByZipCodeAdapter, updateCustomerAdapter) ;
        
    }
}
