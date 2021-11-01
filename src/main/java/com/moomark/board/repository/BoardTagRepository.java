package com.moomark.board.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.moomark.board.domain.Board;
import com.moomark.board.domain.BoardTag;
import com.moomark.board.domain.Tag;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long>{
  List<BoardTag> findByBoard(Board board);
  List<BoardTag> findByTag(Tag tag);
  Optional<BoardTag> findByBoardAndTag(Board board, Tag tag);
}
