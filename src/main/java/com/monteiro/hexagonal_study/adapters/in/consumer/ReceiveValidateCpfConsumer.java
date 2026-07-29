package com.monteiro.hexagonal_study.adapters.in.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.monteiro.hexagonal_study.adapters.in.consumer.mapper.CustomerMessageMapper;
import com.monteiro.hexagonal_study.adapters.in.consumer.message.CustomerMessage;
import com.monteiro.hexagonal_study.application.ports.in.UpdateCustomerInputPort;

@Component
public class ReceiveValidateCpfConsumer {

    @Autowired
    private UpdateCustomerInputPort updateCustomerInputPort;

    @Autowired
    private CustomerMessageMapper customerMessageMapper;

    @KafkaListener(topics = "tp-cpf-validated", groupId = "monteiro")
    public void receive(CustomerMessage customerMessage){
       var customer = customerMessageMapper.toCustomer(customerMessage);
       updateCustomerInputPort.update(customer, customerMessage.getZipCode());
    }

}
