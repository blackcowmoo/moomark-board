package com.moomark.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moomark.post.exception.ErrorCode;
import com.moomark.post.exception.JpaException;
import com.moomark.post.model.dto.PostDto;
import com.moomark.post.model.entity.Category;
import com.moomark.post.model.entity.Post;
import com.moomark.post.model.entity.PostCategory;
import com.moomark.post.model.option.SearchKey;
import com.moomark.post.model.option.SearchOption;
import com.moomark.post.model.option.SortOption;
import com.moomark.post.repository.PostCategoryRepository;
import com.moomark.post.repository.PostRepository;
import com.moomark.post.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

  private final PostRepository postRepository;
  private final CategoryRepository categoryRepository;
  private final PostCategoryRepository postCategoryRepository;

  private final Integer MAX_LIMIT = 100; // Posts per page

  public Post savePost(PostDto postDto) {
    return savePost(postDto.getUserId(), postDto.getTitle(), postDto.getContent());
  }

  public Post savePost(String userId, String title, String content) {
    return postRepository.save(Post.builder().title(title).userId(userId).content(content).build());
  }

  public List<Post> getPosts(Long offset, Integer limit) {
    return getPostsWithOptions(offset, limit, null, null);
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
    return PostDto.builder().id(post.getId()).userId(post.getUserId())
        .content(post.getContent()).title(post.getTitle()).uploadTime(post.getUploadTime())
        .recommendCount(post.getRecommendCount()).viewsCount(post.getViewsCount()).build();
  }

  public List<PostDto> getPostInfoByTitle(String title) {
    var postList = postRepository.findByTitle(title);
    List<PostDto> postDtoList = new ArrayList<>();
    for (Post post : postList) {
      var postDto = PostDto.builder().id(post.getId()).title(post.getTitle()).userId(post.getUserId())
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
          .title(post.getPost().getTitle()).userId(post.getPost().getUserId())
          .recommendCount(post.getPost().getRecommendCount())
          .viewsCount(post.getPost().getViewsCount()).build());
    }

    return resultList;
  }

  private List<Post> getPostsWithOptions(Long offset, Integer limit, SearchOption search, SortOption order) {
    if (offset == null) {
      offset = Long.MAX_VALUE;
    }

    if (limit == null || limit < 0 || limit > MAX_LIMIT) {
      limit = MAX_LIMIT;
    }

    List<Order> orders = new ArrayList<>();
    if (order != null) {

      orders.add(new Order(order.getAsc() ? Direction.ASC : Direction.DESC, order.getKey().name()));
    }

    orders.add(new Order(Direction.DESC, SearchKey.ID.name()));

    if (search != null) {
      switch (search.getKey()) {
        case USER_ID:
          return postRepository.findByUserIdAndIdLessThan(
              search.getValue(), offset, Pageable.ofSize(limit), Sort.by(orders));
        default:
          break;
      }
    }

    return postRepository.findByIdLessThan(offset, Pageable.ofSize(limit), Sort.by(orders));
  }

}
