package com.moomark.board.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.moomark.board.domain.Board;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder 
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardDto implements DtoInterface<Board> {
  private Long id;

  private Long authorId;

  private Long recommendCount;

  private Long viewsCount;

  private String title;

  private String content;

  private LocalDateTime uploadTime;

  private List<CategoryDto> categories;

  private List<TagDto> tags;

  @Override
  public Board toEntity() {
    return Board.builder().authorId(this.authorId).title(this.title).content(this.content).build();
  }
}
