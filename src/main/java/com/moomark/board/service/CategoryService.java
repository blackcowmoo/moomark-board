package com.moomark.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moomark.board.domain.Category;
import com.moomark.board.domain.CategoryDto;
import com.moomark.board.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
	private final CategoryRepository categoryRepository;
	
	/**
	 * category 추가 함수
	 * @param information
	 * @return
	 */
	@Transactional
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
	@Transactional
	public Long updateCategory(Long categoryId, String information) {
		var category = categoryRepository.getById(categoryId);
		category.updateCategoryInfo(information);
		return categoryId;
	}
	
	
	/**
	 * category 삭제 함수
	 * @param id
	 */
	@Transactional
	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
	}
	
	
	/**
	 * child category 추가 함수
	 * @param parentId
	 * @param childId
	 */
	@Transactional
	public void addChildCategory(Long parentId, Long childId) {
		var parentCategory = categoryRepository.findById(parentId).orElseThrow();
		var childCategory = categoryRepository.findById(childId).orElseThrow();
		
		parentCategory.addChildCategory(childCategory);
	}
	
	/**
	 * ID 기반의 Category 정보 가져오기
	 * @param Id
	 * @return
	 */
	public CategoryDto getCategoryById(Long Id) {
		var category = categoryRepository.getById(Id);
		
		return CategoryDto.builder()
				.id(category.getId())
				.parentsId(category.getParentIdCheckNull())
				.categoryType(category.getCategoryType())
				.build();
		
	}
}
