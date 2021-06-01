package com.gyanendrokh.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gyanendrokh.auth.dto.AuthLoginDto;
import com.gyanendrokh.auth.dto.LoginJwtDto;
import com.gyanendrokh.auth.exception.AuthenticationException;
import com.gyanendrokh.auth.repository.UserRepository;
import com.gyanendrokh.auth.user.User;
import com.gyanendrokh.auth.user.UserEntity;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;

  public static Algorithm getJwtAlgorithm() {
    return Algorithm.HMAC256("secret");
  }

  public UserEntity verify(AuthLoginDto data) throws AuthenticationException {
    return repository.findByUsername(data.getUsername())
      .filter(u -> passwordEncoder.matches(data.getPassword(), u.getPassword()))
      .orElseThrow(() -> new AuthenticationException("Username and Password does not match."));
  }

  public String generateLoginJwt(User user) {

    return JWT.create()
      .withSubject(user.getUsername())
      .withExpiresAt(new DateTime().plusHours(1).toDate())
      .sign(getJwtAlgorithm());
  }

  public LoginJwtDto logIn(AuthLoginDto dto) throws AuthenticationException {
    UserEntity entity = verify(dto);
    User user = new User(entity);

    String jwt = generateLoginJwt(user);

    return new LoginJwtDto(jwt);
  }

}
