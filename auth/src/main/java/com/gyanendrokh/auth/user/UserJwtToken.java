package com.gyanendrokh.auth.user;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UserJwtToken extends UsernamePasswordAuthenticationToken {

  public UserJwtToken(User user, String jwt, Collection<? extends GrantedAuthority> authorities) {
    super(user, jwt, authorities);
  }

}
