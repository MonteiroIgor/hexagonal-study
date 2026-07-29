package com.monteiro.hexagonal_study.adapters.in.consumer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.monteiro.hexagonal_study.adapters.in.consumer.message.CustomerMessage;
import com.monteiro.hexagonal_study.application.core.domain.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMessageMapper {


    @Mapping(target = "address", ignore = true)
    Customer toCustomer(CustomerMessage customerMessage);
}
