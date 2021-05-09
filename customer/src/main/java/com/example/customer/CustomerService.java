package com.example.customer;

import com.example.common.Customer;
import java.util.Collection;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface CustomerService {
  Mono<Customer> get(UUID uuid);
  Mono<Customer> save(Customer customer);
  Mono<Customer> save(String uuid);
  Mono<Collection<Customer>> getAll();
}
