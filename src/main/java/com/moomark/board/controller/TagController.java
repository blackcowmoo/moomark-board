package com.moomark.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.moomark.board.service.TagService;
import com.moomark.board.util.StringUtils;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TagController {
  private final TagService tagService;

  // TODO : TagController �옉�꽦 �븘�슂

  /* Get */

  /* Post */
  @PostMapping("tag/information")
  public ResponseEntity<Long> addTagInformation(@RequestBody String information) {
    information = StringUtils.removeSpecialCharacter(information);
    Long result = tagService.addTag(information.trim());
    return new ResponseEntity<> (result, HttpStatus.OK);
  }
  /* Put */

  /* Delete */
}
