package com.monteiro.hexagonal_study.application.ports.out;

public interface SendCpfForValidationOutputPort {

    void send(String cpf);

}
