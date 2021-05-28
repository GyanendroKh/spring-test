package com.gyanendrokh.auth.dto;

import com.gyanendrokh.auth.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

  private String username;

  public static UserDto from(User user) {
    return new UserDto(user.getUsername());
  }

}
