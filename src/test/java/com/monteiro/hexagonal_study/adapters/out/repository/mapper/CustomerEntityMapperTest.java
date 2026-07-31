package com.monteiro.hexagonal_study.adapters.out.repository.mapper;

import com.monteiro.hexagonal_study.adapters.out.repository.entity.CustomerEntity;
import com.monteiro.hexagonal_study.application.core.domain.Address;
import com.monteiro.hexagonal_study.application.core.domain.Customer;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerEntityMapperTest {

    private final CustomerEntityMapper mapper = Mappers.getMapper(CustomerEntityMapper.class);

    @Test
    void shouldMapAddressAndValidationToEntity() {
        Customer customer = new Customer();
        customer.setId("1");
        customer.setName("Igor Monteiro");
        customer.setCpf("08675504497");
        customer.setAddress(new Address("Rua Exemplo", "São Paulo", "SP"));
        customer.setIsValidCpf(true);

        CustomerEntity entity = mapper.toCustomerEntity(customer);

        assertNotNull(entity);
        assertNotNull(entity.getAddress());
        assertEquals("Rua Exemplo", entity.getAddress().getStreet());
        assertEquals("São Paulo", entity.getAddress().getCity());
        assertEquals("SP", entity.getAddress().getState());
        assertTrue(entity.getIsValidCpf());
    }
}
