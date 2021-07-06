package com.moomark.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.board.domain.BoardTag;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long>{

}
