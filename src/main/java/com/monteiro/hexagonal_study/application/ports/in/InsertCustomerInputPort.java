package com.monteiro.hexagonal_study.application.ports.in;

import com.monteiro.hexagonal_study.application.core.domain.Customer;

public interface InsertCustomerInputPort {

    void insert(Customer customer, String zipCode);
}
