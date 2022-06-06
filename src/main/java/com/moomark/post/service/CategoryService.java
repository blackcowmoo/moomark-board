package com.moomark.post.service;

import org.springframework.stereotype.Service;

import com.moomark.post.exception.ErrorCode;
import com.moomark.post.exception.JpaException;
import com.moomark.post.model.dto.CategoryDto;
import com.moomark.post.model.entity.Category;
import com.moomark.post.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;

  public CategoryDto getCategoryById(Long id) throws JpaException {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_CATEGORY.getMsg(),
            ErrorCode.CANNOT_FIND_CATEGORY.getCode()));

    return CategoryDto.builder().categoryType(category.getCategoryType()).id(category.getId())
        .parentsId(category.getParentAfterNullCheck()).build();
  }

  public Long addCategory(String information) {
    var category = Category.builder().type(information).build();

    return categoryRepository.save(category).getId();
  }

  public void updateCategory(Long categoryId, String information) throws JpaException {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_CATEGORY.getMsg(),
            ErrorCode.CANNOT_FIND_CATEGORY.getCode()));

    category.updateCategoryInfo(information);
  }

  public void deleteCategory(Long id) {
    categoryRepository.deleteById(id);
  }

  public void addChildCategory(Long parentId, Long childId) throws JpaException {
    var parentCategory = categoryRepository.findById(parentId)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_PARENT_CATEGORY.getMsg(),
            ErrorCode.CANNOT_FIND_PARENT_CATEGORY.getCode()));

    var childCategory = categoryRepository.findById(childId)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_CHILD_CATEGORY.getMsg(),
            ErrorCode.CANNOT_FIND_CHILD_CATEGORY.getCode()));

    parentCategory.addChildCategory(childCategory);
  }

  public boolean deleteChildCategory(Long parentId, Long childId) throws JpaException {

    var parentCategory = categoryRepository.findById(parentId)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_PARENT_CATEGORY.getMsg(),
            ErrorCode.CANNOT_FIND_PARENT_CATEGORY.getCode()));
    var childCategory = categoryRepository.findById(childId)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_CHILD_CATEGORY.getMsg(),
            ErrorCode.CANNOT_FIND_CHILD_CATEGORY.getCode()));
    parentCategory.removeChildCategory(childCategory);
    return true;
  }

}
