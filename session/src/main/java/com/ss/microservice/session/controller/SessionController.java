package com.ss.microservice.session.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {
  @GetMapping
  public String greetingAdmin() {
    return "Hello Admin";
  }
}
