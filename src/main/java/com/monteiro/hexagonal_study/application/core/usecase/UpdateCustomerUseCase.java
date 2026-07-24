package com.monteiro.hexagonal_study.application.core.usecase;

import com.monteiro.hexagonal_study.application.core.domain.Customer;
import com.monteiro.hexagonal_study.application.ports.in.FindCustomerByIdInputPort;
import com.monteiro.hexagonal_study.application.ports.in.UpdateCustomerInputPort;
import com.monteiro.hexagonal_study.application.ports.out.FindAddressByZipCodeOutputPort;
import com.monteiro.hexagonal_study.application.ports.out.UpdateCustomerOutputPort;

public class UpdateCustomerUseCase implements UpdateCustomerInputPort {

    private final FindCustomerByIdInputPort findCustomerByIdInputPort;

    private final FindAddressByZipCodeOutputPort findAddressByZipCodeOutputPort;

    private final UpdateCustomerOutputPort updateCustomerOutputPort;

    public UpdateCustomerUseCase(FindCustomerByIdInputPort findCustomerByIdInputPort, FindAddressByZipCodeOutputPort findAddressByZipCodeOutputPort, UpdateCustomerOutputPort updateCustomerOutputPort) {
        this.findCustomerByIdInputPort = findCustomerByIdInputPort;
        this.findAddressByZipCodeOutputPort = findAddressByZipCodeOutputPort;
        this.updateCustomerOutputPort = updateCustomerOutputPort;
    }

    @Override
    public void update(Customer customer, String zipCode) {
        findCustomerByIdInputPort.find(customer.getId());
        var address = findAddressByZipCodeOutputPort.find(zipCode);
        customer.setAddress(address);
    }

}
