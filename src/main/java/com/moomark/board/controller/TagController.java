package com.moomark.board.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.moomark.board.domain.TagDto;
import com.moomark.board.exception.JpaException;
import com.moomark.board.service.TagService;
import com.moomark.board.util.StringUtils;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TagController {
  private final TagService tagService;

  /* Get */
  @GetMapping("tag/inforamtion")
  public ResponseEntity<List<TagDto>> getTagInformationList() {
    return new ResponseEntity<>(tagService.getTagInformationList(), HttpStatus.OK);
  }


  /* Post */
  @PostMapping("tag/information")
  public ResponseEntity<Long> addTagInformation(@RequestBody String information) {
    information = StringUtils.removeSpecialCharacter(information);
    Long result = tagService.saveTag(information.trim());
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  /* Put */
  @PutMapping("tag/information")
  public ResponseEntity<Long> updateTagInformation(@RequestBody Long tagId,
      @RequestBody String information) throws JpaException {
    information = StringUtils.removeSpecialCharacter(information);
    Long result = tagService.updateTagById(tagId, information);
    return new ResponseEntity<>(result, HttpStatus.OK);

  }


  /* Delete */
  @DeleteMapping("tag/information")
  public ResponseEntity<Long> deleteTagInformationById(@RequestBody Long tagId)
      throws JpaException {
    Long deleteTagId = tagService.deleteTag(tagId);
    return new ResponseEntity<>(deleteTagId, HttpStatus.OK);
  }
}
