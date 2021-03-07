package com.ss.microservice.user.service;

import com.ss.microservice.user.entity.User;
import com.ss.microservice.user.repository.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  public User save(User entity) {
    return repository.save(entity);
  }

  public Optional<User> getById(Long id) {
    return repository.findById(id);
  }
}
