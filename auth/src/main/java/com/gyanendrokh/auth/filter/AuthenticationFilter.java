package com.gyanendrokh.auth.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.gyanendrokh.auth.service.AuthService;
import com.gyanendrokh.auth.user.User;
import com.gyanendrokh.auth.user.UserDetailsService;
import com.gyanendrokh.auth.user.UserJwtToken;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
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
    authenticate(request);

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(@NotNull HttpServletRequest request) {
    try {
      token = extractToken(request);
      user = verifyAndLoadUser(token);

      return false;
    } catch (InvalidTokenException | UsernameNotFoundException | JWTVerificationException e) {
      return true;
    }
  }

  private String extractToken(HttpServletRequest request) throws InvalidTokenException {
    String authorization = request.getHeader("Authorization");

    if (!Strings.isNullOrEmpty(authorization)) {
      Iterator<String> auths = Splitter.on(" ")
        .omitEmptyStrings()
        .trimResults()
        .split(authorization)
        .iterator();

      if (auths.hasNext() && auths.next().toLowerCase(Locale.ROOT).equals("bearer")) {
        if (auths.hasNext()) {
          String next = auths.next();

          if (!auths.hasNext()) {
            return next;
          }
        }
      }
    }

    throw new InvalidTokenException();
  }

  private void authenticate(HttpServletRequest request) {
    var authentication = new UserJwtToken(user, token, user.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private DecodedJWT verifyToken(String raw) throws JWTVerificationException {
    JWTVerifier verifier = JWT.require(AuthService.getJwtAlgorithm())
      .build();

    return verifier.verify(raw);
  }

  private User loadUser(DecodedJWT jwt) {
    return userService.loadUserByUsername(jwt.getSubject());
  }

  private User verifyAndLoadUser(String raw) {
    return loadUser(verifyToken(raw));
  }

  private static class InvalidTokenException extends Exception {
    // Nothing to do
  }
}
