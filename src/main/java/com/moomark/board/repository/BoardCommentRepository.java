package com.moomark.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.board.domain.BoardComment;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {

}
