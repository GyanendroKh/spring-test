package com.gyanendrokh.auth.service;

import com.gyanendrokh.auth.dto.BaseUserRegisterDto;
import com.gyanendrokh.auth.exception.UsernameExistException;
import com.gyanendrokh.auth.repository.BaseUserRepository;
import com.gyanendrokh.auth.user.BaseUser;
import com.gyanendrokh.auth.user.BaseUserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

  private final PasswordEncoder passwordEncoder;
  private final BaseUserRepository repository;

  public BaseUser register(BaseUserRegisterDto data) {
    if (repository.findByUsername(data.getUsername()).isPresent()) {
      throw new UsernameExistException(data.getUsername());
    }

    BaseUserEntity userEntity = new BaseUserEntity(
      data.getUsername(), passwordEncoder.encode(data.getPassword()));
    BaseUser user = new BaseUser(userEntity);

    repository.save(userEntity);

    return user;
  }

}
