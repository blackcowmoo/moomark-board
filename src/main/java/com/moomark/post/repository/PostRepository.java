package com.moomark.post.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
  public List<Post> findByTitle(String title);

  public List<Post> findByUserId(String userId);

  public Optional<Post> findById(Long id);
}
