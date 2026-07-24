package com.monteiro.hexagonal_study.adapters.in.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.monteiro.hexagonal_study.adapters.in.controller.request.CustomerRequest;
import com.monteiro.hexagonal_study.application.core.domain.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "address", ignore = true),
        @Mapping(target = "isValidCpf", ignore = true)
    })
    Customer toCustomer(CustomerRequest customerRequest);

}
