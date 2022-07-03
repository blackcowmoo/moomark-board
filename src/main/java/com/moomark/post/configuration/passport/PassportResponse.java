package com.moomark.post.configuration.passport;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class PassportResponse {
  public Timestamp exp; // expired timestamp
  public User user;
}
