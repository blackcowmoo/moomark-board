package com.moomark.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moomark.board.domain.BoardDto;
import com.moomark.board.domain.CommentDto;
import com.moomark.board.service.BoardService;
import com.moomark.board.service.CommentService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;
  private final CommentService commentService;

  /*
   * =================================== GET ===================================
   */
  @GetMapping("/board/{boardId}/content")
  public BoardDto getBoardInfoById(@PathVariable("boardId") Long boardId) {
    return boardService.getBoardInfoById(boardId);
  }

  @GetMapping("/board/{boardId}/info")
  public RequestTotalBoardInfo getTotalBoardInfoById(@PathVariable("boardId") Long boardId)
      throws Exception {
    RequestTotalBoardInfo result = new RequestTotalBoardInfo();
    result.setBoardInfo(boardService.getBoardInfoById(boardId));
    result.setTotalCommentCount(commentService.getCommentCountByBoardId(boardId));
    result.setCommentList(commentService.getCommentByBoardId(boardId));

    return result;
  }

  /*
   * =================================== POST ==================================
   */
  @PostMapping("/board/new")
  public Long saveBoardInfo(BoardDto boardDto) {
    return boardService.saveBoard(boardDto);
  }

  @DeleteMapping("/board/{boardId}")
  public void deleteBoardInfoById(@PathVariable("boardId") Long boardId) {
    boardService.deleteBoard(boardId);
  }

  /*
   * ============================= Static class ================================
   */
  @Data
  private class RequestTotalBoardInfo {
    BoardDto boardInfo;
    int totalCommentCount;
    List<CommentDto> commentList;
  }
}
