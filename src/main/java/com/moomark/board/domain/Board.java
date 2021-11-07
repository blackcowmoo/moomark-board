package com.moomark.board.domain;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  @Column(name = "author_id")
  private Long authorId;

  @NonNull
  @Column(name = "recommend_count")
  private Long recommendCount;

  @NonNull
  @Column(name = "views_count")
  private Long viewsCount;

  @NonNull
  @Column(name = "title")
  private String title;

  @NonNull
  @Column(name = "content")
  private String content;

  @NonNull
  @Column(name = "upload_time")
  private LocalDateTime uploadTime;

  @Column(name = "category_id")
  @OneToMany(mappedBy = "board")
  private List<BoardCategory> boardCategory = new ArrayList<>();
  
  @Column(name = "comment_id")
  @OneToMany(mappedBy = "comment")
  private List<BoardComment> boardComment = new ArrayList<>();
  
  @Column(name = "tag_id")
  @OneToMany(mappedBy = "tag")
  private List<BoardTag> boardTag = new ArrayList<>();

  @Builder
  public Board(Long authorId, String title, String content) {
    this.authorId = authorId;
    this.title = title;
    this.content = content;
    this.recommendCount = (long) 0;
    this.viewsCount = (long) 0;
    this.uploadTime = LocalDateTime.now();
  }

  /* Function List */
  public void upCountViewCount() {
    this.viewsCount++;
  }

  public void downCountViewCount() {
    if (0 < this.viewsCount)
      this.viewsCount--;
  }

  public void updateInformation(String title, String content) {
    this.title = title;
    this.content = content;
    this.uploadTime = LocalDateTime.now();
  }
}
