package com.monteiro.hexagonal_study.application.ports.out;

import com.monteiro.hexagonal_study.application.core.domain.Address;

public interface FindAddressByZipCodeOutputPort {

    Address find(String zipCode);


}
