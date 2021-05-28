package com.gyanendrokh.auth.dto;

import com.gyanendrokh.auth.user.BaseUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseUserDto {

  private String username;

  public static BaseUserDto from(BaseUser user) {
    return new BaseUserDto(user.getUsername());
  }

}
