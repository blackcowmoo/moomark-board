package com.moomark.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.board.domain.BoardCategory;

public interface BoardCategoryRepository extends JpaRepository<BoardCategory, Long> {

}
