package com.gyanendrokh.auth;

import com.gyanendrokh.auth.repository.BaseUserRepository;
import com.gyanendrokh.auth.user.BaseUserEntity;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Fixture {

  private final PasswordEncoder passwordEncoder;
  private final BaseUserRepository repository;

  @Bean
  CommandLineRunner runner() {
    return e -> {
      String password = passwordEncoder.encode("password");
      BaseUserEntity user = new BaseUserEntity("username", password);

      repository.save(user);
    };
  }
}
