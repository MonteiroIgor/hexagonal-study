package com.monteiro.hexagonal_study.adapters.out;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.monteiro.hexagonal_study.adapters.out.repository.CustomerRepository;
import com.monteiro.hexagonal_study.application.ports.out.DeleteCustomerByIdOutputPort;

@Component
public class DeleteCustomerByIdAdapter implements DeleteCustomerByIdOutputPort {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void delete(String id) {
        customerRepository.deleteById(id);
    }
}
