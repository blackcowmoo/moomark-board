package com.moomark.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.post.model.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
