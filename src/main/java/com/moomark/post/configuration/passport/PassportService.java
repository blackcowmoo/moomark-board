package com.moomark.post.configuration.passport;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PassportService {

  @Autowired
  private ObjectMapper mapper;

  @Autowired
  private PassportRepository passportRepository;

  public User parsePassport(String passport) {
    try {
      PassportResponse passportResult = mapper
          .readValue(passportRepository.decryptByPublicKey(Base64.getDecoder().decode(passport)),
              PassportResponse.class);

      if (passportResult.getExp().after(Timestamp.valueOf(LocalDateTime.now()))) {
        return passportResult.getUser();
      }
    } catch (Exception e) {
      log.error("parsePassport", e);
    }

    return null;
  }
}
