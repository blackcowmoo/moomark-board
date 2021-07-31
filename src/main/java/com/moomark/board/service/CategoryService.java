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
	 * category �߰� �Լ�
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
	 * category�� ���� ����
	 * @param categoryId
	 * @param information
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public Long updateCategory(Long categoryId, String information) throws Exception {
		var category = categoryRepository.findById(categoryId).orElseThrow(()
				-> new Exception("�����ϰ��� �ϴ� ī�װ� ������ �����ϴ�."));
		category.updateCategoryInfo(information);
		return categoryId;
	}
	
	
	/**
	 * category ���� �Լ�
	 * @param id
	 * @throws Exception 
	 */
	@Transactional
	public void deleteCategory(Long id) throws Exception {
		getCategoryById(id);
		categoryRepository.deleteById(id);
	}
	
	
	/**
	 * child category �߰� �Լ�
	 * @param parentId
	 * @param childId
	 */
	@Transactional
	public void addChildCategory(Long parentId, Long childId) {
		var parentCategory = categoryRepository.findById(parentId).orElseThrow();
		var childCategory = categoryRepository.findById(childId).orElseThrow();
		
		// �ڽ� ī�װ��� �θ� ī�װ��� ���� ��� �θ� ī�װ� �߰�
		if(childCategory.getParentAfterNullCheck() == 0)
			parentCategory.addChildCategory(childCategory);
		else
			throw new IllegalStateException("�������� �θ� ī�װ��� ���� �� �����ϴ�.");
	}
	
	/**
	 * ID ����� Category ���� ��������
	 * @param Id
	 * @return
	 */
	public CategoryDto getCategoryById(Long id) throws Exception {
		var category = categoryRepository.findById(id).orElseThrow(() 
				-> new Exception("ī�װ� ������ �����ϴ�."));
		
		return CategoryDto.builder()
				.id(category.getId())
				.parentsId(category.getParentAfterNullCheck())
				.categoryType(category.getCategoryType())
				.build();
		
	}
}
