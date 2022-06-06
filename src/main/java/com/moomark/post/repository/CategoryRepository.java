package com.moomark.post.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.post.model.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  public Optional<Category> findByCategoryType(String categoryType);

  public Optional<Category> findById(Long id);
}
