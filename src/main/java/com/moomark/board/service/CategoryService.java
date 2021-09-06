package com.moomark.board.service;

import org.springframework.stereotype.Service;

import com.moomark.board.domain.Category;
import com.moomark.board.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryRepository categoryRepository;
	
	/**
	 * Add category 
	 * @param information
	 * @return
	 */
	public Long addCategory(String information) {
		var category = Category.builder()
				.type(information)
				.build();
		
		return categoryRepository.save(category).getId();
	}
	
	/**
	 * Delete category 
	 * @param id
	 */
	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
	}
	
	
	/**
	 * Add child category
	 * @param parentId
	 * @param childId
	 */
	public void addChildCategory(Long parentId, Long childId) {
		var parentCategory = categoryRepository.findById(parentId).orElseThrow();
		var childCategory = categoryRepository.findById(childId).orElseThrow();
		
		parentCategory.addChildCategory(childCategory);
	}
}
