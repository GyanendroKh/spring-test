package com.example.first;

import com.example.common.Order;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import com.example.common.Customer;

@SpringBootApplication
public class FirstApplication {
	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class, args);
	}
}

@RestController
class Controller {
	@GetMapping("/")
	Mono<String> hello() {
		return Mono.just("Hello");
	}

	@GetMapping("/customer")
	Mono<Customer> customer() {
		return Mono.just(Customer.fake());
	}

	@GetMapping("/order")
	Mono<Order> order() {
		return Mono.just(Order.fake());
	}

	@GetMapping("/order/{customer}")
	Mono<Order> orderCustomer(@PathVariable String customer) {
		return Mono.just(Order.fake(UUID.fromString(customer)));
	}
}
