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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class BoardComment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name = "board_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Board board;
	
	@JoinColumn(name = "comment_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Comment comment;
	
	@Builder
	public BoardComment(Board board, Comment comment) {
		this.board = board;
		this.comment = comment;
	}

}
