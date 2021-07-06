package com.moomark.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.board.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
