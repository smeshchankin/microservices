package com.ss.microservice.user.controller;

import com.ss.microservice.user.entity.User;
import com.ss.microservice.user.service.UserService;
import com.ss.microservice.user.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequestMapping("/users")
@RestController
public class UserController {
  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @PostMapping
  public User create(@RequestBody User entity) {
    log.info("Create user: " + entity);
    return service.save(entity);
  }

  @GetMapping("/{id}")
  public User readOne(@PathVariable Long id) {
    log.info("Read user by id = " + id);
    return service.getById(id)
      .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "User not found with id = " + id
      ));
  }

  @GetMapping("/with-department/{id}")
  public UserVO readUserWithDepartment(@PathVariable Long id) {
    log.info("Read user with department by id = " + id);
    return service.getUserWithDepartment(id)
      .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "User not found with id = " + id
      ));
  }
}
