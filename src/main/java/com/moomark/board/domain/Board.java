package com.moomark.board.domain;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "author_id")
	private Long authorId;
	
	@Column(name = "recommend_count")
	private Long recommendCount;
	
	@Column(name = "views_count")
	private Long viewsCount;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "upload_time")
	private LocalDateTime uploadTime;
	
	@Builder
	public Board (Long authorId, Long recommedCount, Long viewsCount, String title, String content) {
		this.authorId = authorId;
		this.recommendCount = recommedCount;
		this.viewsCount = viewsCount;
		this.title = title;
		this.content = content;
		this.uploadTime = LocalDateTime.now();
	}
	
}