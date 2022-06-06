package com.moomark.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moomark.post.repository.PostCommentRepository;
import com.moomark.post.repository.PostRepository;
import com.moomark.post.model.dto.CommentDto;
import com.moomark.post.model.entity.Comment;
import com.moomark.post.model.entity.Post;
import com.moomark.post.model.entity.PostComment;
import com.moomark.post.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final PostCommentRepository postCommentRepository;

  public List<CommentDto> getCommentByPostId(Long postId) throws Exception {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new Exception("No comment information was found by post id."));

    List<PostComment> postCommentList = postCommentRepository.findByPost(post);
    List<CommentDto> resultList = new ArrayList<>();
    for (PostComment postComment : postCommentList) {
      Comment comment = postComment.getComment();
      CommentDto commentDto = CommentDto.builder().id(comment.getId()).content(comment.getContent())
          .parentsId(comment.getParentId()).build();
      resultList.add(commentDto);
    }
    return resultList;
  }

  public List<CommentDto> getCommentByPostId(Long postId, int pageNumber) throws Exception {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new Exception("No comment information was found by post id."));

    PageRequest pageRequest = PageRequest.of(pageNumber, 20);
    Page<PostComment> postCommmentPage = postCommentRepository.findByPost(post, pageRequest);
    List<CommentDto> resultList = new ArrayList<>();
    for (PostComment postComment : postCommmentPage) {
      Comment comment = postComment.getComment();
      CommentDto commentDto = CommentDto.builder().id(comment.getId()).content(comment.getContent())
          .parentsId(comment.getParentId()).build();
      resultList.add(commentDto);
    }

    return resultList;
  }

  public int getCommentCountByPostId(Long postId) throws Exception {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new Exception("No post information was found by post id."));

    return postCommentRepository.findByPost(post).size();
  }

  public List<CommentDto> getCommentByUserId(Long postId, String userId) throws Exception {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new Exception("No comment information was found by post id"));

    List<PostComment> postCommentList = postCommentRepository.findByPost(post);

    List<CommentDto> resultList = new ArrayList<>();
    for (PostComment postComment : postCommentList) {
      Comment comment = postComment.getComment();
      if (comment.getUserId() == userId) {
        CommentDto commentDto = CommentDto.builder().id(comment.getId())
            .content(comment.getContent()).parentsId(comment.getParentId()).build();
        resultList.add(commentDto);
      }
    }
    return resultList;
  }

  public Long saveComment(CommentDto commentDto) {
    Comment comment =
        Comment.builder().content(commentDto.getContent()).userId(commentDto.getUserId()).build();
    return commentRepository.save(comment).getId();
  }

  public Boolean deleteComment(Long id) {
    try {
      var comment = commentRepository.findById(id)
          .orElseThrow(() -> new Exception("No comment information was found by comment id."));
      commentRepository.deleteById(comment.getId());
      return true;
    } catch (Exception e) {
      log.error("delete error {}", e);
      return false;
    }
  }
}
