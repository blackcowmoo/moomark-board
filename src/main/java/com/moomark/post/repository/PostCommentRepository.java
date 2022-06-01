package com.moomark.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.post.domain.Post;
import com.moomark.post.domain.PostComment;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
  List<PostComment> findByPost(Post post);

  Page<PostComment> findByPost(Post post, Pageable pageAble);
}
