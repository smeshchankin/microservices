package com.ss.microservice.user.service;

import com.ss.microservice.user.entity.User;
import com.ss.microservice.user.repository.UserRepository;
import com.ss.microservice.user.vo.DepartmentVO;
import com.ss.microservice.user.vo.UserVO;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
  private static final String DEPARTMENTS_URL = "http://localhost:9001/departments/";

  private final UserRepository repository;
  private final RestTemplate rest;

  public UserService(UserRepository repository, RestTemplate rest) {
    this.repository = repository;
    this.rest = rest;
  }

  public User save(User entity) {
    return repository.save(entity);
  }

  public Optional<User> getById(Long id) {
    return repository.findById(id);
  }

  public Optional<UserVO> getUserWithDepartment(Long id) {
    return repository.findById(id)
      .map(user -> {
        UserVO vo = user.toVO();

        String url = DEPARTMENTS_URL + user.getDepartmentId();
        DepartmentVO department = rest.getForObject(url, DepartmentVO.class);
        vo.setDepartment(department);

        return vo;
      });
  }
}
