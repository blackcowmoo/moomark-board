package com.moomark.post.repository;


import com.moomark.post.model.entity.Post;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class PostRepositoryTest {

  @Autowired
  private PostRepository postRepository;

  @BeforeEach
  void refreshDataBase() {
    postRepository.deleteAll();
  }

  @Test
  void findByTitle() {
    // given
    Post firstSavedData = Post.builder()
        .title("FIRST TITLE")
        .content("FIRST CONTENT")
        .userId("FIRST USER ID")
        .build();

    Post secondSavedData = Post.builder()
        .title("SECOND TITLE")
        .content("SECOND CONTENT")
        .userId("SECOND USER ID")
        .build();

    // when
    postRepository.save(firstSavedData);
    postRepository.save(secondSavedData);

    // then
    List<Post> savedList = postRepository.findByTitleContaining("TITLE");
    Assertions.assertThat(savedList).hasSize(2);
  }

  @Test
  void findByUserId() {

    //given
    Post firstSavedData = Post.builder()
        .title("FIRST TITLE")
        .content("FIRST CONTENT")
        .userId("FIRST USER ID")
        .build();
    Post secondSavedData = Post.builder()
        .title("SECOND TITLE")
        .content("SECOND CONTENT")
        .userId("SECOND USER ID")
        .build();
    Post thirdSavedData = Post.builder()
        .title("THIRD TITLE")
        .content("THIRD CONTENT")
        .userId("FIRST USER ID")
        .build();

    //when
    postRepository.save(firstSavedData);
    postRepository.save(secondSavedData);
    postRepository.save(thirdSavedData);

    //then
    List<Post> savedList = postRepository.findByUserId("FIRST USER ID");
    Assertions.assertThat(savedList).hasSize(2);
  }
}