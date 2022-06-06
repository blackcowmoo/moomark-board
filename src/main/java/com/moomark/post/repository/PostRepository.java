package com.moomark.post.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.post.model.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
  public List<Post> findByTitle(String title);

  public List<Post> findByUserId(String userId);

  public Optional<Post> findById(Long id);

  public List<Post> findByIdGreaterThan(Long id, Pageable paging);

  public List<Post> findByIdLessThan(Long id, Pageable paging);

  public List<Post> findByUserIdAndIdLessThan(String userId, Long id, Pageable paging);
}
