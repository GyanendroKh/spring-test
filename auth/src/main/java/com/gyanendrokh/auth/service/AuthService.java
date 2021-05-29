package com.gyanendrokh.auth.service;

import com.gyanendrokh.auth.dto.AuthLoginDto;
import com.gyanendrokh.auth.exception.AuthenticationException;
import com.gyanendrokh.auth.repository.UserRepository;
import com.gyanendrokh.auth.user.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;

  public UserEntity verify(AuthLoginDto data) {
    return repository.findByUsername(data.getUsername())
      .filter(u -> passwordEncoder.matches(data.getPassword(), u.getPassword()))
      .orElseThrow(() -> new AuthenticationException("Username and Password does not match."));
  }

}
