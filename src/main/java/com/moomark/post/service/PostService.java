package com.moomark.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moomark.post.domain.Post;
import com.moomark.post.domain.PostCategory;
import com.moomark.post.domain.PostDto;
import com.moomark.post.domain.Category;
import com.moomark.post.exception.ErrorCode;
import com.moomark.post.exception.JpaException;
import com.moomark.post.repository.PostCategoryRepository;
import com.moomark.post.repository.PostRepository;
import com.moomark.post.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

  private final PostRepository postRepository;
  private final CategoryRepository categoryRepository;
  private final PostCategoryRepository postCategoryRepository;

  public Long savePost(PostDto postDto) {
    log.info("add Post : {}", postDto);

    var post = Post.builder().title(postDto.getTitle()).authorId(postDto.getAuthorId())
        .content(postDto.getContent()).build();

    return postRepository.save(post).getId();
  }

  public void deletePost(Long postId) throws JpaException {
    var post = postRepository.findById(postId)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_POST.getMsg(),
            ErrorCode.CANNOT_FIND_POST.getCode()));
    postRepository.delete(post);
  }

  public PostDto getPostInfoById(Long id) throws JpaException {
    var post = postRepository.findById(id)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_POST.getMsg(),
            ErrorCode.CANNOT_FIND_POST.getCode()));
    post.upCountViewCount();
    return PostDto.builder().id(post.getId()).authorId(post.getAuthorId())
        .content(post.getContent()).title(post.getTitle()).uploadTime(post.getUploadTime())
        .recommendCount(post.getRecommendCount()).viewsCount(post.getViewsCount()).build();
  }

  public List<PostDto> getPostInfoByTitle(String title) {
    var postList = postRepository.findByTitle(title);
    List<PostDto> postDtoList = new ArrayList<>();
    for (Post post : postList) {
      var postDto =
          PostDto.builder().id(post.getId()).title(post.getTitle()).authorId(post.getAuthorId())
              .content(post.getContent()).uploadTime(post.getUploadTime())
              .viewsCount(post.getViewsCount()).recommendCount(post.getRecommendCount()).build();
      postDtoList.add(postDto);

    }

    return postDtoList;
  }

  public void addCategoryToPost(Long postId, Long categoryId) throws JpaException {
    var post = postRepository.findById(postId)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_POST.getMsg()));
    var category = categoryRepository.findById(categoryId).orElseThrow();

    postCategoryRepository.save(PostCategory.builder().post(post).category(category).build());
  }

  public void deleteCategoryToPost(Long postId, Long categoryId) throws JpaException {
    var post = postRepository.findById(postId)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_POST.getMsg(),
            ErrorCode.CANNOT_FIND_POST.getCode()));
    var category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_CATEGORY.getMsg(),
            ErrorCode.CANNOT_FIND_CATEGORY.getCode()));

    var postCategory = postCategoryRepository.findByPostAndCategory(post, category);
    postCategoryRepository.delete(postCategory);
  }

  public List<PostDto> getPostListByCategory(Long categoryId) throws JpaException {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new JpaException(ErrorCode.CANNOT_FIND_CATEGORY.getMsg(),
            ErrorCode.CANNOT_FIND_CATEGORY.getCode()));
    List<PostCategory> getPostList = postCategoryRepository.findByCategory(category);
    List<PostDto> resultList = new ArrayList<>();
    for (PostCategory post : getPostList) {
      resultList.add(PostDto.builder().id(post.getPost().getId())
          .title(post.getPost().getTitle()).authorId(post.getPost().getAuthorId())
          .recommendCount(post.getPost().getRecommendCount())
          .viewsCount(post.getPost().getViewsCount()).build());
    }

    return resultList;
  }
}
