package com.moomark.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.post.model.entity.PostTag;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

}
