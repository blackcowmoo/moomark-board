package com.moomark.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.post.domain.Board;
import com.moomark.post.domain.BoardCategory;
import com.moomark.post.domain.Category;

public interface BoardCategoryRepository extends JpaRepository<BoardCategory, Long> {
	public List<BoardCategory> findByBoard(Board board);
	public List<BoardCategory> findByCategory(Category category);
	public BoardCategory findByBoardAndCategory(Board board, Category category);
}
