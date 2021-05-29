package com.gyanendrokh.auth.dto;

import lombok.Data;

@Data
public class AuthLoginDto {

  private final String username;
  private final String password;

}
