package com.monteiro.hexagonal_study.adapters.out.repository.mapper;

import com.monteiro.hexagonal_study.adapters.out.repository.entity.CustomerEntity;
import com.monteiro.hexagonal_study.application.core.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerEntityMapper {

    CustomerEntity toCustomerEntity(Customer customer);

    Customer toCustomer(CustomerEntity customerEntity);
}
