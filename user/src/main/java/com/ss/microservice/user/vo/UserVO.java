package com.ss.microservice.user.vo;

import lombok.Data;

@Data
public class UserVO {
  private Long id;
  private String firstname;
  private String lastname;
  private String email;
  private DepartmentVO department;
}
