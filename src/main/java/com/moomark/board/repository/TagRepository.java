package com.moomark.board.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.board.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{
  Optional<Tag> findByInformation(String information);
}
