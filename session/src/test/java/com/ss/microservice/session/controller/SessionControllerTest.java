package com.ss.microservice.session.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import redis.clients.jedis.Jedis;

public class SessionControllerTest {
  private static final String USERNAME = "admin";
  private static final String PASSWORD = "password";

  private Jedis redis;
  private TestRestTemplate rest;
  private TestRestTemplate restWithAuth;
  private String url = "http://localhost:8080";

  @BeforeEach
  public void clearRedisData() {
    rest = new TestRestTemplate();
    restWithAuth = new TestRestTemplate(USERNAME, PASSWORD);
    redis = new Jedis("localhost", 6379);
    redis.flushAll();
  }

  @Test
  public void testRedisIsEmpty() {
    Set<String> result = redis.keys("*");
    assertEquals(0, result.size());
  }

  @Test
  public void testUnauthenticatedCantAccess() {
    ResponseEntity<String> result = this.rest.getForEntity(url, String.class);
    assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
  }
}
