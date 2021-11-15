package com.moomark.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.moomark.board.dto.CategoryDto;
import com.moomark.board.exception.JpaException;
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
  public ResponseEntity<CategoryDto> getCategoryInfo(@PathVariable("id") Long categoryId)
      throws JpaException {
    return new ResponseEntity<>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
  }

  /* Post */
  @PostMapping("/category/{info}")
  public Long addCategoryInfo(@PathVariable("info") String cateogryInfo) {
    return categoryService.addCategory(cateogryInfo);
  }

  @PostMapping("/category/child")
  public ResponseEntity<String> addChildCategory(@RequestBody RequestChildCategory request)
      throws JpaException {
    categoryService.addChildCategory(request.parentId, request.childId);
    return new ResponseEntity<>("Success to add child category", HttpStatus.OK);
  }

  @PostMapping("/category/mapping")
  public void addCategoryToBoard(@RequestBody RequestAddCategoryToBoard requestInformation)
      throws JpaException {
    boardService.saveCategoryToBoard(requestInformation.getBoardId(),
        requestInformation.getCategoryId());
  }

  /* Put */
  @PutMapping("/category/child")
  public void updateCategoryInfo(@RequestBody RequestCategoryInfo requestCategoryInfo)
      throws JpaException {
    categoryService.updateCategory(requestCategoryInfo.getCategoryId(),
        requestCategoryInfo.getCategoryInfo());
  }

  /* Delete */
  @DeleteMapping("/category")
  public void deleteCateogory(@RequestBody RequestCategoryInfo requestCategoryInfo)
      throws JpaException {
    categoryService.deleteCategory(requestCategoryInfo.getCategoryId());
  }

  @DeleteMapping("/category/child")
  public void deleteChildCategory(@RequestBody RequestChildCategory requset) throws JpaException {
    categoryService.deleteChildCategory(requset.parentId, requset.childId);

  }
}
