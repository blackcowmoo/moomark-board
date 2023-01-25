package com.moomark.post.model.dto;

import com.moomark.post.model.entity.Post;
import com.moomark.post.model.entity.PostCategory;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
public class PostDto {
  private Long id;
  
  private String userId;
  
  private Long recommendCount;
  
  private Long viewsCount;
  
  private String title;
  
  private String content;
  
  private LocalDateTime uploadTime;
  
  private List<CategoryDto> categories;

  public static PostDto toDto(Post post) {
    List<CategoryDto> categoryDtos = new ArrayList<>();
    for (PostCategory category : post.getPostCategory()) {
      categoryDtos.add(CategoryDto.builder()
          .id(category.getId())
          .categoryType(category.getCategory().getCategoryType())
          .parentsId(category.getCategory().getParent().getId())
          .build()
      );
    }

    return PostDto.builder()
        .id(post.getId())
        .userId(post.getUserId())
        .recommendCount(post.getRecommendCount())
        .viewsCount(post.getViewsCount())
        .title(post.getTitle())
        .content(post.getContent())
        .uploadTime(post.getUploadTime())
        .categories(categoryDtos)
        .build();
  }
}
