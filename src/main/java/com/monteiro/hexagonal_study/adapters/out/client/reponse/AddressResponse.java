package com.monteiro.hexagonal_study.adapters.out.client.reponse;

import lombok.Data;

@Data
public class AddressResponse {

    private String street;
    private String city;
    private String state;

}
