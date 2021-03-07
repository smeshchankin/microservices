package com.ss.microservice.department.service;

import com.ss.microservice.department.entity.Department;
import com.ss.microservice.department.repository.DepartmentRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
  private final DepartmentRepository repository;

  public DepartmentService(DepartmentRepository repository) {
    this.repository = repository;
  }

  public Department save(Department entity) {
    return repository.save(entity);
  }

  public Optional<Department> getById(Long id) {
    return repository.findById(id);
  }
}
