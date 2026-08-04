package com.monteiro.hexagonal_study.application.core.usecase;

import com.monteiro.hexagonal_study.application.core.domain.Address;
import com.monteiro.hexagonal_study.application.core.domain.Customer;
import com.monteiro.hexagonal_study.application.ports.in.FindCustomerByIdInputPort;
import com.monteiro.hexagonal_study.application.ports.out.FindAddressByZipCodeOutputPort;
import com.monteiro.hexagonal_study.application.ports.out.UpdateCustomerOutputPort;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class UpdateCustomerUseCaseTest {

    @Test
    void shouldLookupCustomerAndAttachResolvedAddress() {
        Customer customer = new Customer("1", "John", null, "12345678900", true);
        Address address = new Address("New Street", "Rio", "RJ");
        String[] requestedId = new String[1];

        FindCustomerByIdInputPort findCustomerPort = id -> {
            requestedId[0] = id;
            return new Customer();
        };
        FindAddressByZipCodeOutputPort findAddressPort = zipCode -> address;
        UpdateCustomerOutputPort updateCustomerPort = customerToUpdate -> {
        };

        UpdateCustomerUseCase useCase = new UpdateCustomerUseCase(findCustomerPort, findAddressPort, updateCustomerPort);

        useCase.update(customer, "20000-000");

        assertEquals("1", requestedId[0]);
        assertSame(address, customer.getAddress());
    }
}
