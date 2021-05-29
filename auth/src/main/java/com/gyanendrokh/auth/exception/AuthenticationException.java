package com.gyanendrokh.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthenticationException extends ResponseStatusException {

  public AuthenticationException(String msg) {
    super(HttpStatus.UNAUTHORIZED, msg);
  }
}
