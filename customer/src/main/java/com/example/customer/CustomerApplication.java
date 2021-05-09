package com.example.customer;

import com.example.common.Customer;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class CustomerApplication {

  private final CustomerService customerService;

  public CustomerApplication(CustomerService customerService) {
    this.customerService = customerService;
  }

  public static void main(String[] args) {
    SpringApplication.run(CustomerApplication.class, args);
  }

  @Bean
  ApplicationListener<ApplicationReadyEvent> onReady() {
    return this::onApplicationEvent;
  }

  private void onApplicationEvent(ApplicationReadyEvent event) {
    customerService.save(Customer.fake());
    customerService.save(Customer.fake());
    customerService.save(Customer.fake());
    customerService.save(Customer.fake());
  }
}

@Service
class CustomerServiceImpl implements CustomerService {

  private final HashMap<UUID, Customer> customers = new HashMap<>();

  @Override
  public Mono<Customer> get(UUID uuid) {
    return Mono.just(customers.get(uuid));
  }

  @Override
  public Mono<Customer> save(Customer customer) {
    customers.put(customer.getUuid(), customer);

    return Mono.just(customer);
  }

  @Override
  public Mono<Customer> save(String uuid) {
    var customer = Customer.fake();
    customer.setUuid(UUID.fromString(uuid));

    return Mono.just(customer);
  }

  @Override
  public Mono<Collection<Customer>> getAll() {
    return Mono.just(customers.values());
  }
}

@RestController
class Controller {

  private final CustomerService customerService;

  public Controller(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/customers")
  Mono<Collection<Customer>> getAll() {
    return customerService.getAll();
  }
}
