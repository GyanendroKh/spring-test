package com.gyanendrokh.auth.filter;

import com.gyanendrokh.auth.repository.UserRepository;
import com.gyanendrokh.auth.user.User;
import com.gyanendrokh.auth.user.UserEntity;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationFilter extends OncePerRequestFilter {

  private final UserRepository userRepo;

  private String token = "";
  private UserEntity userEntity = null;

  public AuthenticationFilter(UserRepository userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  protected void doFilterInternal(
    @NotNull HttpServletRequest request,
    @NotNull HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    System.out.println("Token " + token);
    User user = new User(userEntity);

    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

    var authentication = new UsernamePasswordAuthenticationToken(user, token, authorities);
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String authorization = request.getHeader("Authorization");

    if (authorization != null) {
      String[] auths = authorization.split(" ");

      if (auths.length == 2) {
        if (auths[0].toLowerCase(Locale.ROOT).equals("bearer")) {
          token = auths[1];
          Optional<UserEntity> user = userRepo.findByUsername(auths[1]);

          if (user.isPresent()) {
            userEntity = user.get();

            return false;
          }
        }
      }
    }

    return true;
  }
}
