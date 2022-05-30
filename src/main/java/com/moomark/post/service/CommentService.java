package com.moomark.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moomark.post.domain.Board;
import com.moomark.post.domain.BoardComment;
import com.moomark.post.domain.Comment;
import com.moomark.post.domain.CommentDto;
import com.moomark.post.repository.BoardCommentRepository;
import com.moomark.post.repository.BoardRepository;
import com.moomark.post.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
  private final BoardRepository boardRepository;
  private final CommentRepository commentRepository;
  private final BoardCommentRepository boardCommentRepository;

  /**
   * Get comment list by board id
   * 
   * @param boardId
   * @return
   * @throws Exception
   */
  public List<CommentDto> getCommentByBoardId(Long boardId) throws Exception {
    Board board = boardRepository.findById(boardId)
        .orElseThrow(() -> new Exception("No comment information was found by board id."));

    List<BoardComment> boardCommentList = boardCommentRepository.findByBoard(board);
    List<CommentDto> resultList = new ArrayList<>();
    for (BoardComment boardComment : boardCommentList) {
      Comment comment = boardComment.getComment();
      CommentDto commentDto = CommentDto.builder().id(comment.getId()).content(comment.getContent())
          .parentsId(comment.getParentId()).build();
      resultList.add(commentDto);
    }
    return resultList;
  }

  /**
   * Get comment list by page to board id
   * 
   * @param boardId
   * @param pageNumber
   * @return
   * @throws Exception
   */
  public List<CommentDto> getCommentByBoardId(Long boardId, int pageNumber) throws Exception {
    Board board = boardRepository.findById(boardId)
        .orElseThrow(() -> new Exception("No comment information was found by board id."));

    PageRequest pageRequest = PageRequest.of(pageNumber, 20);
    Page<BoardComment> boardCommmentPage = boardCommentRepository.findByBoard(board, pageRequest);
    List<CommentDto> resultList = new ArrayList<>();
    for (BoardComment boardComment : boardCommmentPage) {
      Comment comment = boardComment.getComment();
      CommentDto commentDto = CommentDto.builder().id(comment.getId()).content(comment.getContent())
          .parentsId(comment.getParentId()).build();
      resultList.add(commentDto);
    }

    return resultList;
  }

  /**
   * Get comment count by board id
   * 
   * @param boardId
   * @return
   * @throws Exception
   */
  public int getCommentCountByBoardId(Long boardId) throws Exception {
    Board board = boardRepository.findById(boardId)
        .orElseThrow(() -> new Exception("No board information was found by board id."));

    return boardCommentRepository.findByBoard(board).size();
  }

  /**
   * Get comment information by user id
   * 
   * @param userId
   * @return
   * @throws Exception
   */
  public List<CommentDto> getCommentByUserId(Long boardId, Long userId) throws Exception {
    Board board = boardRepository.findById(boardId)
        .orElseThrow(() -> new Exception("No comment information was found by board id"));

    List<BoardComment> boardCommentList = boardCommentRepository.findByBoard(board);

    List<CommentDto> resultList = new ArrayList<>();
    for (BoardComment boardComment : boardCommentList) {
      Comment comment = boardComment.getComment();
      if (comment.getUserId() == userId) {
        CommentDto commentDto = CommentDto.builder().id(comment.getId())
            .content(comment.getContent()).parentsId(comment.getParentId()).build();
        resultList.add(commentDto);
      }
    }
    return resultList;
  }

  /**
   * save comment
   * 
   * @return
   */
  public Long saveComment(CommentDto commentDto) {
    Comment comment =
        Comment.builder().content(commentDto.getContent()).userId(commentDto.getUserId()).build();
    return commentRepository.save(comment).getId();
  }

  /**
   * delete comment
   * 
   * @param id
   * @return
   */
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
