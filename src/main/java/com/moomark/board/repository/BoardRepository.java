package com.moomark.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
	public List<Board> findByTitle(String title);
	public List<Board> findByAuthorId(Long authorId);
}
