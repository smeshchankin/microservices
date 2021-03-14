package com.ss.microservice.session.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private static final String ROLE = "ADMIN";
  private static final String USERNAME = "admin";
  private static final String PASSWORD = "password";

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
      .withUser(USERNAME)
      .password(passwordEncoder().encode(PASSWORD))
      .roles(ROLE);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().and()
      .authorizeRequests()
      .antMatchers("/").hasRole(ROLE)
      .anyRequest().authenticated();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
