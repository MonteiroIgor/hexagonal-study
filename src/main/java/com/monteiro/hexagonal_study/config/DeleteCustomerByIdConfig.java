package com.monteiro.hexagonal_study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.monteiro.hexagonal_study.adapters.out.DeleteCustomerByIdAdapter;
import com.monteiro.hexagonal_study.application.core.usecase.DeleteCustomerByIdUseCase;
import com.monteiro.hexagonal_study.application.core.usecase.FindCustomerByIdUseCase;

@Configuration          
public class DeleteCustomerByIdConfig {

    @Bean
    public DeleteCustomerByIdUseCase deleteCustomerByIdUseCase(
        FindCustomerByIdUseCase findCustomerByIdUseCase,
        DeleteCustomerByIdAdapter deleteCustomerByIdAdapter
    ){
        return new DeleteCustomerByIdUseCase(findCustomerByIdUseCase, deleteCustomerByIdAdapter);
        
    }


}
