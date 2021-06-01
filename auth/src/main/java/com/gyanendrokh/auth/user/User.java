package com.gyanendrokh.auth.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {

  private final String username;
  private final String password;
  private final Collection<SimpleGrantedAuthority> authorities;

  public User(String username, String password, Set<SimpleGrantedAuthority> authorities) {
    this.username = username;
    this.password = password;
    this.authorities = authorities;

    this.authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
  }

  public User(UserEntity entity, Set<SimpleGrantedAuthority> authorities) {
    this(entity.getUsername(), entity.getPassword(), authorities);
  }

  public User(UserEntity entity) {
    this(entity, new HashSet<>());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
