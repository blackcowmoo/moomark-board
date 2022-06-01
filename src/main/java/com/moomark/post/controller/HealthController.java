package com.moomark.post.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HealthController {
  @GetMapping("/health")
  public String getCategoryInfo() {
    return "OK";
  }
}
