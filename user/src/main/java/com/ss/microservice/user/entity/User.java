package com.ss.microservice.user.entity;

import com.ss.microservice.user.vo.UserVO;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String firstname;
  private String lastname;
  private String email;
  private Long departmentId;

  public UserVO toVO() {
    UserVO vo = new UserVO();
    vo.setId(id);
    vo.setFirstname(firstname);
    vo.setLastname(lastname);
    vo.setEmail(email);

    return vo;
  }
}
