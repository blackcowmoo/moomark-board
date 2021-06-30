package com.moomark.board.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Builder;

public class Comment {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "category")
	private List<BoardComment> board;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "content")
	private String content;
	
	@JoinColumn(name = "parent_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Comment parent;
	
	@OneToMany(mappedBy = "parent")
	private List<Comment> childList;
	
	@Builder
	public Comment(Long userId, String content) {
		this.userId = userId;
		this.content = content;
	}
}
