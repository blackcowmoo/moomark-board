package com.moomark.board.service;

import org.springframework.stereotype.Service;

import com.moomark.board.domain.Category;
import com.moomark.board.domain.CategoryDto;
import com.moomark.board.exception.ErrorCode;
import com.moomark.board.exception.JpaException;
import com.moomark.board.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;

  /**
   * Get category information by category id;
   * 
   * @param Id
   * @return
   * @throws Exception
   */
  public CategoryDto getCategoryById(Long id) throws JpaException {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_CATEGORY.getMsg(),
            ErrorCode.CANNOT_FIND_CATEGORY.getCode()));

    return CategoryDto.builder().categoryType(category.getCategoryType()).id(category.getId())
        .parentsId(category.getParentAfterNullCheck()).build();
  }

  /**
   * Add category
   * 
   * @param information
   * @return
   */
  public Long addCategory(String information) {
    var category = Category.builder().type(information).build();

    return categoryRepository.save(category).getId();
  }

  /**
   * Update category information
   * 
   * @param categoryId
   * @param information
   * @throws Exception
   */
  public void updateCategory(Long categoryId, String information) throws JpaException {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_CATEGORY.getMsg(),
            ErrorCode.CANNOT_FIND_CATEGORY.getCode()));

    category.updateCategoryInfo(information);
  }

  /**
   * Delete category
   * 
   * @param id
   */
  public void deleteCategory(Long id) {
    categoryRepository.deleteById(id);
  }

  /**
   * Add child category
   * 
   * @param parentId
   * @param childId
   * @throws JpaException 
   */
  public void addChildCategory(Long parentId, Long childId) throws JpaException {
    var parentCategory = categoryRepository.findById(parentId)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_CATEGORY.getMsg(),
            ErrorCode.CANNOT_FIND_CATEGORY.getCode()));
    var childCategory = categoryRepository.findById(childId)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_CATEGORY.getMsg(),
            ErrorCode.CANNOT_FIND_CATEGORY.getCode()));

    parentCategory.addChildCategory(childCategory);
  }

  /**
   * Delete child category
   * 
   * @param parentId
   * @param childId
   * @return
   * @throws Exception
   */
  public boolean deleteChildCategory(Long parentId, Long childId) throws JpaException {

    var parentCategory = categoryRepository.findById(parentId)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_CATEGORY.getMsg(),
            ErrorCode.CANNOT_FIND_CATEGORY.getCode()));
    var childCategory = categoryRepository.findById(childId)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_CATEGORY.getMsg(),
            ErrorCode.CANNOT_FIND_CATEGORY.getCode()));
    parentCategory.removeChildCategory(childCategory);
    return true;
  }
}
