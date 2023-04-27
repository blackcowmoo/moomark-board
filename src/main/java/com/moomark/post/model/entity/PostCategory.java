package com.moomark.post.model.entity;

import com.moomark.post.model.dto.PostDto;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class PostCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "post_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Post post;

  @JoinColumn(name = "category_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Category category;

  @Builder
  public PostCategory(Post post, Category category) {
    this.post = post;
    this.category = category;
  }

  public PostDto toPostDto() {
    return PostDto.builder()
        .id(this.post.getId())
        .title(this.post.getTitle())
        .userId(this.post.getUserId())
        .recommendCount(this.post.getRecommendCount())
        .viewsCount(this.post.getViewsCount())
        .build();
  }
}
