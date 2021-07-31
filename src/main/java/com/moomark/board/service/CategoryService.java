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
	 * @throws Exception 
	 */
	@Transactional
	public Long updateCategory(Long categoryId, String information) throws Exception {
		var category = categoryRepository.findById(categoryId).orElseThrow(()
				-> new Exception("변경하고자 하는 카테고리 정보가 없습니다."));
		category.updateCategoryInfo(information);
		return categoryId;
	}
	
	
	/**
	 * category 삭제 함수
	 * @param id
	 * @throws Exception 
	 */
	@Transactional
	public void deleteCategory(Long id) throws Exception {
		getCategoryById(id);
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
		
		// 자식 카테고리에 부모 카테고리가 없는 경우 부모 카테고리 추가
		if(childCategory.getParentAfterNullCheck() == 0)
			parentCategory.addChildCategory(childCategory);
		else
			throw new IllegalStateException("여러가지 부모 카테고리를 가질 수 없습니다.");
	}
	
	/**
	 * ID 기반의 Category 정보 가져오기
	 * @param Id
	 * @return
	 */
	public CategoryDto getCategoryById(Long id) throws Exception {
		var category = categoryRepository.findById(id).orElseThrow(() 
				-> new Exception("카테고리 정보가 없습니다."));
		
		return CategoryDto.builder()
				.id(category.getId())
				.parentsId(category.getParentAfterNullCheck())
				.categoryType(category.getCategoryType())
				.build();
		
	}
}
