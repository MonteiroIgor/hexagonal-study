package com.monteiro.hexagonal_study.application.ports.in;

import com.monteiro.hexagonal_study.application.core.domain.Customer;

public interface FindCustomerByIdInputPort {

    Customer find(String id);

}
