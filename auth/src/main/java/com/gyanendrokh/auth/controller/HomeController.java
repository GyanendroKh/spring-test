package com.gyanendrokh.auth.controller;

import com.gyanendrokh.auth.dto.UserDto;
import com.gyanendrokh.auth.dto.UserRegistrationDto;
import com.gyanendrokh.auth.service.RegistrationService;
import com.gyanendrokh.auth.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HomeController {

  private final RegistrationService registrationService;

  @GetMapping("/")
  UserDto index(@AuthenticationPrincipal User user) {
    return UserDto.from(user);
  }

  @PostMapping("/register")
  UserDto register(@RequestBody UserRegistrationDto data) {
    return UserDto.from(registrationService.register(data));
  }

}
