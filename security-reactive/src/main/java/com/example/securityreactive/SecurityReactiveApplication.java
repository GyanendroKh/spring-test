package com.example.securityreactive;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class SecurityReactiveApplication {

  public static void main(String[] args) {
    SpringApplication.run(SecurityReactiveApplication.class, args);
  }

}

@RestController
class Controller {

  @GetMapping("/")
  Mono<String> welcome() {
    return Mono.just("Welcome");
  }

  @GetMapping("/hello")
  Mono<User> hello() {
    return ReactiveSecurityContextHolder.getContext()
      .map(securityContext -> (User) securityContext.getAuthentication().getPrincipal());
  }

  @GetMapping("/user/*")
  Mono<User> user() {
    return ReactiveSecurityContextHolder.getContext()
      .map(securityContext -> (User) securityContext.getAuthentication().getPrincipal());
  }
}

@Configuration
@EnableWebFluxSecurity
class HelloWebfluxSecurityConfig {

  @Bean
  public MapReactiveUserDetailsService userDetailsService() {
    UserDetails user = User.withDefaultPasswordEncoder()
      .username("test")
      .password("Password")
      .roles("USER")
      .build();

    UserDetails user2 = User.withDefaultPasswordEncoder()
      .username("test2")
      .password("Password")
      .roles("USER")
      .build();

    return new MapReactiveUserDetailsService(user, user2);
  }

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    return http.authorizeExchange(e -> e
      .pathMatchers("/hello").authenticated()
      .pathMatchers("/user/{username}").access((
        (authentication, context) -> authentication
          .map(a -> (User) a.getPrincipal())
          .map(a -> {
            var decision = a.getUsername().equals(context.getVariables().get("username"));
            return new AuthorizationDecision(decision);
          })
      ))
      .anyExchange().permitAll()
    ).httpBasic(withDefaults()).build();
  }

}
