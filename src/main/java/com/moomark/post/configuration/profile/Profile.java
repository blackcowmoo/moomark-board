package com.moomark.post.configuration.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Profile {
  LOCAL("local"),
  DEVELOP("dev"),
  PROD("prod");

  private final String value;

  public boolean isEqualTo(String profile) {
    return this.value.equals(profile);
  }
}
