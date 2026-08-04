package com.monteiro.hexagonal_study.application.core.usecase;

import com.monteiro.hexagonal_study.application.core.domain.Customer;
import com.monteiro.hexagonal_study.application.ports.out.FindCustomerByIdOutputPort;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FindCustomerByIdUseCaseTest {

    @Test
    void shouldReturnCustomerWhenFound() {
        Customer expected = new Customer("1", "John", null, "12345678900", true);
        FindCustomerByIdOutputPort outputPort = id -> Optional.of(expected);

        FindCustomerByIdUseCase useCase = new FindCustomerByIdUseCase(outputPort);

        Customer result = useCase.find("1");

        assertSame(expected, result);
    }

    @Test
    void shouldThrowExceptionWhenCustomerIsNotFound() {
        FindCustomerByIdOutputPort outputPort = id -> Optional.empty();
        FindCustomerByIdUseCase useCase = new FindCustomerByIdUseCase(outputPort);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> useCase.find("missing-id"));

        assertEquals("Customer not found", exception.getMessage());
    }
}
