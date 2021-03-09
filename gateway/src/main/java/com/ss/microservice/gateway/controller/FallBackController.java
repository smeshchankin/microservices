package com.ss.microservice.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {
  @GetMapping("/users/fall-back")
  public String userService() {
    return msg("User");
  }

  @GetMapping("/departments/fall-back")
  public String departmentService() {
    return msg("Department");
  }

  private String msg(String serviceName) {
    return String.format("%s Service is unavailable now. Please try again later.", serviceName);
  }
}
