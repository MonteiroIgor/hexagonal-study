package com.monteiro.hexagonal_study.adapters.in.controller.response;


import lombok.Data;

@Data
public class CustomerResponse {

    private String name;
    private AddressReponse address;
    private String cpf;
    private boolean isValidCpf;

}
