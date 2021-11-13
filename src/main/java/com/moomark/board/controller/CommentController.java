package com.moomark.board.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.moomark.board.exception.JpaException;
import com.moomark.board.service.BoardService;
import com.moomark.board.service.CommentService;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommentController {
  private final CommentService commentService;
  private final BoardService boardService;

  // TODO : Comment Controller 작성 필요
  /* static class */
  @Data
  static class RequestComment {
    @NonNull
    Long boadId;
    @NonNull
    Long authorId;
    String commment;
  }

  /* Get */

  /* Post */
  @PostMapping("board/comment")
  public Long addCommentToBoard(@RequestBody RequestComment requestComment) throws JpaException {
    return commentService.saveComment(requestComment.getBoadId(), requestComment.getAuthorId(),
        requestComment.getCommment());
  }

  /* Put */
  @PostMapping("board/comment")
  public Long updateCommentToBoard(@RequestBody RequestComment requestComment) {
    return null;
  }

  /* Delete */
}
