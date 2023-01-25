package com.moomark.post.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.moomark.post.exception.JpaException;
import com.moomark.post.model.dto.PostDto;
import com.moomark.post.model.entity.Post;
import com.moomark.post.repository.PostRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

  @InjectMocks
  private PostService postService;

  @Mock
  private PostRepository postRepository;

  @Test
  void updatePost() throws JpaException {
    //given
    PostDto savedDto = PostDto.builder()
        .id(1L)
        .userId("Test user")
        .title("Saved title")
        .content("Saved content")
        .recommendCount(1L)
        .build();
    PostDto updateDto = PostDto.builder()
        .id(1L)
        .userId("Test user")
        .title("Update title")
        .content("Update content")
        .recommendCount(1L)
        .build();
    Post savedPost = Post.builder()
        .userId("Test user")
        .title("Saved title")
        .content("Saved content")
        .build();

    //mocking
    given(postRepository.findById(1L)).willReturn(Optional.of(savedPost));

    //when
    Post updatePost = postService.updatePost(updateDto);

    //then
    Assertions.assertThat(updatePost.getTitle()).isEqualTo(updateDto.getTitle());
  }
}