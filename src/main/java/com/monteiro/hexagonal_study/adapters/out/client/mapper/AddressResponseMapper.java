package com.monteiro.hexagonal_study.adapters.out.client.mapper;

import com.monteiro.hexagonal_study.adapters.out.client.reponse.AddressResponse;
import com.monteiro.hexagonal_study.application.core.domain.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressResponseMapper {

    Address toAddress(AddressResponse addressReponse);
}
