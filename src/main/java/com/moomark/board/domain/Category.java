package com.moomark.board.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "category")
	private List<BoardCategory> board;
	
	private String categoryType;
	
	@JoinColumn(name = "parent_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Category parent;
	
	@OneToMany(mappedBy = "parent")
	private List<Category> childList;
	
	
	@Builder
	public Category(String type) {
		this.categoryType = type;
	}
	
	public void updateCategoryInfo(String categoryType) {
		this.categoryType = categoryType;
	}
	
	
	/**
	 * 자식 카테고리 추가 함수
	 * @param childCategory
	 */
	public void addChildCategory(Category childCategory) {
		childCategory.setParents(this);
		this.childList.add(childCategory);
	}
	
	public void removeChildCategory(Category category) {
		this.childList.remove(category);
	}
	
	/**
	 * 부모 카테고리 세팅 함수
	 * @param category
	 */
	private void setParents(Category category) {
		this.parent = category;
	}
}
