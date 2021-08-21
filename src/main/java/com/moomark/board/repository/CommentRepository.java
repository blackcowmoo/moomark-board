package com.moomark.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.board.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	public Optional<Comment> findByUserId(Long userId);
}
 