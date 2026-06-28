package com.monteiro.hexagonal_study.adapters.out;

import com.monteiro.hexagonal_study.adapters.out.client.FindAddressByZipCodeClient;
import com.monteiro.hexagonal_study.adapters.out.client.mapper.AddressResponseMapper;
import com.monteiro.hexagonal_study.application.core.domain.Address;
import com.monteiro.hexagonal_study.application.ports.out.FindAddressByZipCodeOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindAddressByZipCodeAdapter implements FindAddressByZipCodeOutputPort {


    @Autowired
    private FindAddressByZipCodeClient findAddressByZipCodeClient;

    @Autowired
    private AddressResponseMapper addressResponseMapper;

    @Override
    public Address find(String zipCode) {
        var addressResponse = findAddressByZipCodeClient.find(zipCode);
        return addressResponseMapper.toAddress(addressResponse);

    }
}
