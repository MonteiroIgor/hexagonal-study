package com.monteiro.hexagonal_study.application.core.usecase;

import com.monteiro.hexagonal_study.application.ports.in.DeleteCustomerByIdInputPort;
import com.monteiro.hexagonal_study.application.ports.in.FindCustomerByIdInputPort;
import com.monteiro.hexagonal_study.application.ports.out.DeleteCustomerByIdOutputPort;

public class DeleteCustomerByIdUseCase implements DeleteCustomerByIdInputPort{

    private final FindCustomerByIdInputPort findCustomerByIdInputPort;

    private final DeleteCustomerByIdOutputPort deleteCustomerByIdOutputPort;

    public DeleteCustomerByIdUseCase(FindCustomerByIdInputPort findCustomerByIdInputPort, DeleteCustomerByIdOutputPort deleteCustomerByIdOutputPort) {
        this.findCustomerByIdInputPort = findCustomerByIdInputPort;
        this.deleteCustomerByIdOutputPort = deleteCustomerByIdOutputPort;
    }

    @Override
    public void delete(String id){
        findCustomerByIdInputPort.find(id);
        deleteCustomerByIdOutputPort.delete(id);
    }
}
