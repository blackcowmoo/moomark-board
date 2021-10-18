package com.moomark.board.service;

import org.springframework.stereotype.Service;
import com.moomark.board.domain.Category;
import com.moomark.board.domain.CategoryDto;
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
  public CategoryDto getCategoryById(Long id) throws Exception {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new Exception("Can not find by category id"));

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
  public void updateCategory(Long categoryId, String information) throws Exception {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new Exception("Can not find category by catego id"));

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
   */
  public void addChildCategory(Long parentId, Long childId) {
    var parentCategory = categoryRepository.findById(parentId).orElseThrow();
    var childCategory = categoryRepository.findById(childId).orElseThrow();

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
  public boolean deleteChildCategory(Long parentId, Long childId) throws Exception {

    var parentCategory = categoryRepository.findById(parentId)
        .orElseThrow(() -> new Exception("Can not find parent category by category id"));
    var childCategory = categoryRepository.findById(childId)
        .orElseThrow(() -> new Exception("Can not find child cateogry by cateogory id"));
    parentCategory.removeChildCategory(childCategory);
    return true;
  }

}
