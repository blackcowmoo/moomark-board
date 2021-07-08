package com.moomark.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.board.domain.Board;
import com.moomark.board.domain.BoardCategory;
import com.moomark.board.domain.Category;

public interface BoardCategoryRepository extends JpaRepository<BoardCategory, Long> {
	public List<BoardCategory> findByBoard(Board board);
	public List<BoardCategory> findByCategory(Category category);
	public BoardCategory findByBoardAndCategory(Board board, Category category);
}
