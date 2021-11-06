package com.moomark.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moomark.board.domain.Tag;
import com.moomark.board.domain.TagDto;
import com.moomark.board.exception.ErrorCode;
import com.moomark.board.exception.JpaException;
import com.moomark.board.repository.TagRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

  private final TagRepository tagRepository;

  /**
   * Add
   * 
   * @param information
   * @return
   */
  @Transactional
  public Long addTag(String information) {
    var tag = Tag.builder().information(information).build();

    log.info("Add information : {}", information);
    return tagRepository.save(tag).getId();
  }

  /**
   * Delete tag information
   * 
   * @param id
   * @throws JpaException
   */
  @Transactional
  public void deleteTag(Long id) throws JpaException {
    var tag = tagRepository.findById(id)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_TAG_INFORMATION.getMsg(),
            ErrorCode.CANNOT_FIND_TAG_INFORMATION.getCode()));
    tagRepository.deleteById(tag.getId());
  }

  /**
   * find tag information by tag id
   * @param id
   * @return
   * @throws JpaException
   */
  public TagDto findTagById(Long id) throws JpaException {
    var tag = tagRepository.findById(id)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_TAG_INFORMATION.getMsg(),
            ErrorCode.CANNOT_FIND_TAG_INFORMATION.getCode()));
    return TagDto.builder().id(tag.getId()).information(tag.getInformation()).build();
  }
}
