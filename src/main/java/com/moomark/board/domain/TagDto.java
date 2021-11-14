package com.moomark.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TagDto implements DtoInterface<Tag> {
  private Long id;
  private String information;

  @Override
  public Tag toEntity() {
    return Tag.builder().information(this.information).build();
  }
}
