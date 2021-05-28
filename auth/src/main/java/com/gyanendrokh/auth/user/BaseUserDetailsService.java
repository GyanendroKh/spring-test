package com.gyanendrokh.auth.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BaseUserDetailsService implements UserDetailsService {

  private final BaseUserDao userDao;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userDao.findUserByUsername(username)
      .map(BaseUser::new)
      .orElseThrow(() -> new UsernameNotFoundException(getUsernameNotFoundMsg(username)));
  }

  private String getUsernameNotFoundMsg(String username) {
    String USERNAME_NOT_FOUND_MSG = "Username '%s' not found.";

    return String.format(USERNAME_NOT_FOUND_MSG, username);
  }
}
