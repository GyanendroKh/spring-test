package com.gyanendrokh.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegistrationDto {

  private String username;

  private String password;

}
