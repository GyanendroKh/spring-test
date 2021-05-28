package com.gyanendrokh.auth.service;

import com.gyanendrokh.auth.dto.UserRegistrationDto;
import com.gyanendrokh.auth.exception.UsernameExistException;
import com.gyanendrokh.auth.repository.UserRepository;
import com.gyanendrokh.auth.user.User;
import com.gyanendrokh.auth.user.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository repository;

  public User register(UserRegistrationDto data) {
    if (repository.findByUsername(data.getUsername()).isPresent()) {
      throw new UsernameExistException(data.getUsername());
    }

    UserEntity userEntity = new UserEntity(
      data.getUsername(), passwordEncoder.encode(data.getPassword()));
    User user = new User(userEntity);

    repository.save(userEntity);

    return user;
  }

}
