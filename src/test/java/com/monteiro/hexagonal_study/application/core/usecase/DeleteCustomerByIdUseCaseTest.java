package com.monteiro.hexagonal_study.application.core.usecase;

import com.monteiro.hexagonal_study.application.core.domain.Customer;
import com.monteiro.hexagonal_study.application.ports.in.FindCustomerByIdInputPort;
import com.monteiro.hexagonal_study.application.ports.out.DeleteCustomerByIdOutputPort;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteCustomerByIdUseCaseTest {

    @Test
    void shouldVerifyCustomerAndDeleteIt() {
        String[] deletedId = new String[1];
        boolean[] findCalled = new boolean[1];

        FindCustomerByIdInputPort findCustomerPort = id -> {
            findCalled[0] = true;
            return new Customer();
        };
        DeleteCustomerByIdOutputPort deletePort = id -> deletedId[0] = id;

        DeleteCustomerByIdUseCase useCase = new DeleteCustomerByIdUseCase(findCustomerPort, deletePort);

        useCase.delete("1");

        assertTrue(findCalled[0]);
        assertEquals("1", deletedId[0]);
    }
}
