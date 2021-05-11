package com.example.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;

@SpringBootApplication
public class SecurityApplication {

  public static void main(String[] args) {
    SpringApplication.run(SecurityApplication.class, args);
  }

}

@EnableWebSecurity
@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .csrf().disable()
      .addFilter(new TestFilter())
      .addFilterAfter(new TestFilter2(), LogoutFilter.class)
      .authorizeRequests()
      .anyRequest().permitAll();
  }
}

class TestFilter extends UsernamePasswordAuthenticationFilter {

  @Override
  public Authentication attemptAuthentication(
    HttpServletRequest request, HttpServletResponse response
  ) throws AuthenticationException {
    return super.attemptAuthentication(request, response);
  }
}

class TestFilter2 extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
    HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
  ) throws ServletException, IOException {
    response.addHeader("X-TEST-2", "test");

    filterChain.doFilter(request, response);
  }
}

@RestController
class Controller {

  @GetMapping("/hello")
  public String hello() {
    return "Hello";
  }

}
