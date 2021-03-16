package com.ss.microservice.session.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ss.microservice.session.SessionApplication;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.sync.RedisCommands;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SessionApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SessionControllerTest {
  private static final String USERNAME = "admin";
  private static final String PASSWORD = "password";

  RedisCommands<String, String> syncCommand;
  private TestRestTemplate rest;
  private TestRestTemplate restWithAuth;

  @LocalServerPort
  private int port;

  @BeforeEach
  public void clearRedisData() {
    rest = new TestRestTemplate();
    restWithAuth = new TestRestTemplate(USERNAME, PASSWORD);

    RedisClient redis = RedisClient.create("redis://localhost:6379");
    syncCommand = redis.connect().sync();
    syncCommand.flushall();
  }

  @Test
  public void testRedisIsEmpty() {
    List<String> result = syncCommand.keys("*");
    assertEquals(0, result.size());
  }

  @Test
  public void testUnauthenticatedCantAccess() {
    ResponseEntity<String> result = this.rest.getForEntity(getUrl(), String.class);
    assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
  }

  @Test
  public void testRedisControlsSession() {
    ResponseEntity<String> result = restWithAuth.getForEntity(getUrl(), String.class);
    assertEquals("Hello Admin", result.getBody());

    List<String> redisResult = syncCommand.keys("*");
    assertTrue(redisResult.size() > 0);

    String sessionCookie = result.getHeaders().get("Set-Cookie").get(0).split(";")[0];
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cookie", sessionCookie);
    HttpEntity<String> httpEntity = new HttpEntity<>(headers);

    result = rest.exchange(getUrl(), HttpMethod.GET, httpEntity, String.class);
    assertEquals("Hello Admin", result.getBody());

    syncCommand.flushall();

    result = rest.exchange(getUrl(), HttpMethod.GET, httpEntity, String.class);
    assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
  }

  private String getUrl() {
    return "http://localhost:" + port;
  }
}
