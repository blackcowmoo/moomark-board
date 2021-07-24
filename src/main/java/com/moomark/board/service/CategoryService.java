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
	 * category 추가 함수
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
	 * category의 정보 변경
	 * @param categoryId
	 * @param information
	 * @return
	 */
	public Long updateCategory(Long categoryId, String information) {
		var category = categoryRepository.getById(categoryId);
		category.updateCategoryInfo(information);
		return categoryId;
	}
	
	/**
	 * category 삭제 함수
	 * @param id
	 */
	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
	}
	
	
	/**
	 * child category 추가 함수
	 * @param parentId
	 * @param childId
	 */
	public void addChildCategory(Long parentId, Long childId) {
		var parentCategory = categoryRepository.findById(parentId).orElseThrow();
		var childCategory = categoryRepository.findById(childId).orElseThrow();
		
		parentCategory.addChildCategory(childCategory);
	}
}
