package com.moomark.board.dto;

import java.util.List;
import com.moomark.board.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CommentDto implements DtoInterface<Comment> {
  private Long id;
  private Long userId;
  private Long boardId;
  private Long parentsId;
  private List<Long> childIdList;
  private String content;

  @Override
  public Comment toEntity() {
    return Comment.builder()
        .content(this.content)
        .userId(this.userId)
        .build();
  }
}
