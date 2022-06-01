package com.moomark.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moomark.post.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
