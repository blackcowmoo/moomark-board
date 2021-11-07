package com.moomark.board.domain;

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
import lombok.NonNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class BoardCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @NonNull
  @JoinColumn(name = "board_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Board board;


  @NonNull
  @JoinColumn(name = "category_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Category category;

  @Builder
  public BoardCategory(Board board, Category category) {
    this.board = board;
    this.category = category;
  }
}
