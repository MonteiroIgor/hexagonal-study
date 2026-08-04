package com.monteiro.hexagonal_study.application.core.usecase;

import com.monteiro.hexagonal_study.application.core.domain.Address;
import com.monteiro.hexagonal_study.application.core.domain.Customer;
import com.monteiro.hexagonal_study.application.ports.out.FindAddressByZipCodeOutputPort;
import com.monteiro.hexagonal_study.application.ports.out.InsertCustomerOutputPort;
import com.monteiro.hexagonal_study.application.ports.out.SendCpfForValidationOutputPort;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class InsertCustomerUseCaseTest {

    @Test
    void shouldResolveAddressPersistCustomerAndSendCpfForValidation() {
        Customer customer = new Customer("1", "John", null, "12345678900", false);
        Address address = new Address("Main Street", "São Paulo", "SP");
        Customer[] insertedCustomer = new Customer[1];
        String[] sentCpf = new String[1];

        FindAddressByZipCodeOutputPort findAddressPort = zipCode -> address;
        InsertCustomerOutputPort insertCustomerPort = inserted -> insertedCustomer[0] = inserted;
        SendCpfForValidationOutputPort sendCpfPort = cpf -> sentCpf[0] = cpf;

        InsertCustomerUseCase useCase = new InsertCustomerUseCase(findAddressPort, insertCustomerPort, sendCpfPort);

        useCase.insert(customer, "01000-000");

        assertSame(address, customer.getAddress());
        assertSame(customer, insertedCustomer[0]);
        assertEquals("12345678900", sentCpf[0]);
    }
}
