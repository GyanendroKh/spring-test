package com.gyanendrokh.auth.dto;

import com.gyanendrokh.auth.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

  private String username;

  public static UserDto from(User user) {
    return new UserDto(user.getUsername());
  }

}
