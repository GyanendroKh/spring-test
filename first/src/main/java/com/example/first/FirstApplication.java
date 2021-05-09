package com.example.first;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class FirstApplication {
	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class, args);
	}
}

@RestController
class Controller {
	@GetMapping("/")
	Flux<String> hello() {
		return Flux.just("Hello");
	}
}
