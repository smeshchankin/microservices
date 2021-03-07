package com.ss.microservice.department.controller;

import com.ss.microservice.department.entity.Department;
import com.ss.microservice.department.service.DepartmentService;
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
@RestController
@RequestMapping("/departments")
public class DepartmentController {
  private final DepartmentService service;

  public DepartmentController(DepartmentService service) {
    this.service = service;
  }

  @PostMapping
  public Department save(@RequestBody Department entity) {
    log.info("Save department: " + entity);
    return service.save(entity);
  }

  @GetMapping("/{id}")
  public Department readOne(@PathVariable Long id) {
    log.info("Read department by id = " + id);
    return service.getById(id)
      .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "Department not found with id = " + id
    ));
  }
}
