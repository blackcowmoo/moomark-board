package com.moomark.post;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class PassportTestRepository {
  private class Token {
    public String token;
  }

  @Value("${passport.auth-server.google-login}")
  private String loginEndpoint;

  @Value("${passport.auth-server.generate-passport}")
  private String generatePassportEndpoint;

  @Value("${passport.auth-server.withdraw}")
  private String withdrawEndpoint;

  @Autowired
  private RestTemplate restTemplate;

  private String userId = null;

  @PostConstruct
  public void generateUserId() {
    Random random = new Random();
    userId = String.valueOf(random.nextInt(Integer.MAX_VALUE));
  }

  @Override
  protected void finalize() {
    withdrawUser();
  }

  public String loginUser() {
    Map<String, String> params = new HashMap<String, String>();
    params.put("code", "test-" + userId);
    Token tokens = restTemplate
        .exchange(loginEndpoint, HttpMethod.GET, new HttpEntity<>(null, null), Token.class, params).getBody();
    return tokens.token;
  }

  public String generatePassport() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", loginUser());

    return restTemplate.exchange(generatePassportEndpoint, HttpMethod.GET, new HttpEntity<>(headers), String.class)
        .getBody();
  }

  private void withdrawUser() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("x-moom-passport", generatePassport());

    restTemplate.exchange(withdrawEndpoint, HttpMethod.DELETE, new HttpEntity<>(headers), void.class);
  }
}
