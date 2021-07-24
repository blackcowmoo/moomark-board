package com.moomark.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moomark.board.domain.Tag;
import com.moomark.board.domain.TagDto;
import com.moomark.board.repository.TagRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

	private final TagRepository tagRepository;
	
	public Long addTag(String information) {
		var tag = Tag.builder()
				.information(information)
				.build();
		
		log.info("Add information : {}", information);
		return	tagRepository.save(tag).getId();
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
