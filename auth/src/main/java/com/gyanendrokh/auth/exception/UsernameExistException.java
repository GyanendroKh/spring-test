package com.gyanendrokh.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsernameExistException extends ResponseStatusException {

  public UsernameExistException(String username) {
    super(HttpStatus.BAD_REQUEST, String.format("Username '%s' already exist.", username));
  }
}
