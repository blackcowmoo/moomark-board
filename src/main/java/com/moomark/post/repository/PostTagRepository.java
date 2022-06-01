package com.moomark.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.post.domain.PostTag;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

}
