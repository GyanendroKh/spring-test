package com.gyanendrokh.auth.filter;

import com.gyanendrokh.auth.user.User;
import com.gyanendrokh.auth.user.UserDetailsService;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationFilter extends OncePerRequestFilter {

  private final UserDetailsService userService;

  private String token = "";
  private User user = null;

  public AuthenticationFilter(UserDetailsService userDao) {
    this.userService = userDao;
  }

  @Override
  protected void doFilterInternal(
    @NotNull HttpServletRequest request,
    @NotNull HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    var authentication = new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
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

          try {
            user = userService.loadUserByUsername(auths[1]);
            return false;
          } catch (UsernameNotFoundException e) {
            // Nothing to do
          }
        }
      }
    }

    return true;
  }
}
