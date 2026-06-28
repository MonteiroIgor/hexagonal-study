package com.monteiro.hexagonal_study.application.ports.out;

import com.monteiro.hexagonal_study.application.core.domain.Customer;

public interface InsertCustomerOutputPort {

    void insert(Customer customer);


}
