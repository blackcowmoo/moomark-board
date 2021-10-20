package com.moomark.board.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.moomark.board.domain.CategoryDto;
import com.moomark.board.service.BoardService;
import com.moomark.board.service.CategoryService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;
  private final BoardService boardService;

  /* static class */
  @Data
  static class RequestCategoryInfo {
    Long categoryId;
    String categoryInfo;
  }

  @Data
  static class RequestChildCategory {
    Long parentId;
    Long childId;
  }

  @Data
  static class RequestAddCategoryToBoard {
    Long boardId;
    Long categoryId;
  }

  /* Get */
  @GetMapping("/category/{id}")
  public CategoryDto getCategoryInfo(@PathVariable("id") Long categoryId) throws Exception {
    return categoryService.getCategoryById(categoryId);
  }

  /* Post */
  @PostMapping("/category/{info}")
  public Long addCategoryInfo(@PathVariable("info") String cateogryInfo) {
    return categoryService.addCategory(cateogryInfo);
  }

  @PostMapping("/category/child")
  public void addChildCategory(@RequestBody RequestChildCategory request) {
    categoryService.addChildCategory(request.parentId, request.childId);
  }

  @PostMapping("/category/mapping")
  public void addCategoryToBoar(@RequestBody RequestAddCategoryToBoard requestInformation)
      throws Exception {
    boardService.addCategoryToBoard(requestInformation.getBoardId(),
        requestInformation.getCategoryId());
  }

  /* Put */
  @PutMapping("/category/child")
  public void updateCategoryInfo(@RequestBody RequestCategoryInfo requestCategoryInfo)
      throws Exception {
    categoryService.updateCategory(requestCategoryInfo.getCategoryId(),
        requestCategoryInfo.getCategoryInfo());
  }

  /* Delete */
  @DeleteMapping("/category")
  public void deleteCateogory(@RequestBody RequestCategoryInfo requestCategoryInfo)
      throws Exception {
    categoryService.deleteCategory(requestCategoryInfo.getCategoryId());
  }

  @DeleteMapping("/category/child")
  public void deleteChildCategory(@RequestBody RequestChildCategory requset) throws Exception {
    categoryService.deleteChildCategory(requset.parentId, requset.childId);

  }
}
