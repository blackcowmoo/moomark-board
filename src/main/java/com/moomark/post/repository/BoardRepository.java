package com.moomark.post.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.post.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
	public List<Board> findByTitle(String title);
	public List<Board> findByAuthorId(Long authorId);
	public Optional<Board> findById(Long id);
}
