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
	 * category �߰� �Լ�
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
	 * category�� ���� ����
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
	 * category ���� �Լ�
	 * @param id
	 */
	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
	}
	
	
	/**
	 * child category �߰� �Լ�
	 * @param parentId
	 * @param childId
	 */
	public void addChildCategory(Long parentId, Long childId) {
		var parentCategory = categoryRepository.findById(parentId).orElseThrow();
		var childCategory = categoryRepository.findById(childId).orElseThrow();
		
		parentCategory.addChildCategory(childCategory);
	}
}
