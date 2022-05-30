package com.moomark.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.post.domain.BoardTag;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long>{

}
