package com.moomark.post.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardDto {
  private Long id;
  
  private Long authorId;
  
  private Long recommendCount;
  
  private Long viewsCount;
  
  private String title;
  
  private String content;
  
  private LocalDateTime uploadTime;
  
  private List<CategoryDto> categories;
}
