package com.moomark.board.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.moomark.board.domain.Tag;
import com.moomark.board.domain.TagDto;
import com.moomark.board.exception.ErrorCode;
import com.moomark.board.exception.JpaException;
import com.moomark.board.repository.BoardTagRepository;
import com.moomark.board.repository.TagRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

  private final TagRepository tagRepository;
  private final BoardTagRepository boardTagRepository;

  /**
   * Add
   * 
   * @param information
   * @return
   */
  @Transactional
  public Long saveTag(TagDto tag) {
    log.info("Add information : {}", tag.getInformation());
    return tagRepository.save(tag.toEntity()).getId();
  }

  /**
   * Delete tag information
   * 
   * @param id
   * @throws JpaException
   */
  @Transactional
  public Long deleteTag(Long id) throws JpaException {
    var tag = tagRepository.findById(id)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_TAG_INFORMATION.getMsg(),
            ErrorCode.CANNOT_FIND_TAG_INFORMATION.getCode()));

    var boardTagList = boardTagRepository.findByTag(tag);
    if (boardTagList.isEmpty()) {
      throw new JpaException(ErrorCode.EXIST_MATCH_BOARD_AND_TAG.getMsg(),
          ErrorCode.EXIST_MATCH_BOARD_AND_TAG.getCode());
    }

    Long deleteTagId = tag.getId();
    tagRepository.deleteById(deleteTagId);
    return deleteTagId;
  }

  /**
   * find tag information by tag id
   * 
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
  
  /**
   * find tag information by tag information
   * 
   * @param id
   * @return
   * @throws JpaException
   */
  public TagDto findTagByInformation(String information) throws JpaException {
    var tag = tagRepository.findByInformation(information)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_TAG_INFORMATION.getMsg(),
            ErrorCode.CANNOT_FIND_TAG_INFORMATION.getCode()));
    
    return TagDto.builder().id(tag.getId()).information(tag.getInformation()).build();
  }

  /**
   * get tag List
   * 
   * @return
   */
  public List<TagDto> getTagInformationList() {
    var tagList = tagRepository.findAll();
    List<TagDto> resultList = new ArrayList<>();
    for (Tag tag : tagList) {
      resultList.add(TagDto.builder().id(tag.getId()).information(tag.getInformation()).build());
    }
    return resultList;
  }


  /**
   * update tag information
   * 
   * @param id
   * @param information
   * @return
   * @throws JpaException
   */
  @Transactional
  public Long updateTagById(Long id, String information) throws JpaException {
    var tag = tagRepository.findById(id)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_TAG_INFORMATION.getMsg(),
            ErrorCode.CANNOT_FIND_TAG_INFORMATION.getCode()));
    tag.updateInformation(information);
    return tag.getId();
  }
}
