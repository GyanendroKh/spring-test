package com.gyanendrokh.auth.controller;

import com.gyanendrokh.auth.dto.BaseUserDto;
import com.gyanendrokh.auth.dto.BaseUserRegisterDto;
import com.gyanendrokh.auth.service.RegistrationService;
import com.gyanendrokh.auth.user.BaseUser;
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
  BaseUserDto index(@AuthenticationPrincipal BaseUser user) {
    return BaseUserDto.from(user);
  }

  @PostMapping("/register")
  BaseUserDto register(@RequestBody BaseUserRegisterDto data) {
    return BaseUserDto.from(registrationService.register(data));
  }

}
