package com.gyanendrokh.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthLoginDto {

  private final String username;
  private final String password;

}
