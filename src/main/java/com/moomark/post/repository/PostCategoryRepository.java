package com.moomark.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.post.model.entity.Category;
import com.moomark.post.model.entity.Post;
import com.moomark.post.model.entity.PostCategory;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
  public List<PostCategory> findByPost(Post post);

  public List<PostCategory> findByCategory(Category category);

  public PostCategory findByPostAndCategory(Post post, Category category);
}
