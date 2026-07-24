package com.monteiro.hexagonal_study.adapters.out;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.monteiro.hexagonal_study.adapters.out.repository.CustomerRepository;
import com.monteiro.hexagonal_study.adapters.out.repository.mapper.CustomerEntityMapper;
import com.monteiro.hexagonal_study.application.core.domain.Customer;
import com.monteiro.hexagonal_study.application.ports.out.UpdateCustomerOutputPort;

@Component
public class UpdateCustomerAdapter implements UpdateCustomerOutputPort {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerEntityMapper customerEntityMapper;

    @Override
    public void update(Customer customer) {
        var customerEntity = customerEntityMapper.toCustomerEntity(customer);
        customerRepository.save(customerEntity);
    }

}
