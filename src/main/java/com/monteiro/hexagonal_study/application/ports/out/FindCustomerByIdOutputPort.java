package com.monteiro.hexagonal_study.application.ports.out;

import com.monteiro.hexagonal_study.application.core.domain.Customer;
import java.util.Optional;

public interface FindCustomerByIdOutputPort {
    Optional<Customer> find(String id);
}
