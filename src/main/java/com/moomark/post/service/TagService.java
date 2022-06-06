package com.moomark.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moomark.post.model.dto.TagDto;
import com.moomark.post.model.entity.Tag;
import com.moomark.post.repository.TagRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TagService {

  private final TagRepository tagRepository;
  
  public Long addTag(String information) {
    var tag = Tag.builder()
        .information(information)
        .build();
    
    log.info("Add information : {}", information);
    return  tagRepository.save(tag).getId();
  }
  
  public void deleteTag(Long id) {
    tagRepository.deleteById(id);
  }
  
  public TagDto findTagById(Long id) {
    var tag = tagRepository.findById(id).orElseThrow();
    return TagDto.builder()
        .id(tag.getId())
        .information(tag.getInformation())
        .build();
  }
}
