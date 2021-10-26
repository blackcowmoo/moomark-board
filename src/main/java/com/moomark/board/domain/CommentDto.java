package com.moomark.board.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CommentDto {
  private Long id;
  private Long userId;
  private Long boardId;
  private Long parentsId;
  private List<Long> childIdList;
  private String content;
}
