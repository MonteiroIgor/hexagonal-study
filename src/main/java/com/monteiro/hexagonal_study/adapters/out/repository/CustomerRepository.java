package com.monteiro.hexagonal_study.adapters.out.repository;

import com.monteiro.hexagonal_study.adapters.out.repository.entity.CustomerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {
}
