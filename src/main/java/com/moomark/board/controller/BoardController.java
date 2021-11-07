package com.moomark.board.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moomark.board.domain.BoardDto;
import com.moomark.board.domain.CommentDto;
import com.moomark.board.exception.JpaException;
import com.moomark.board.service.BoardService;
import com.moomark.board.service.CommentService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;
  private final CommentService commentService;

  
  // TODO : Comment, Tag 기능 추가 필요
  /*
   * =================================== GET ===================================
   */
  @GetMapping("/board/{boardId}/content")
  public ResponseEntity<BoardDto> getBoardInfoById(@PathVariable("boardId") Long boardId)
      throws JpaException {
    return new ResponseEntity<>(boardService.getBoardInfoById(boardId), HttpStatus.OK);
  }

  @GetMapping("/board/{boardId}/info")
  public ResponseEntity<RequestTotalBoardInfo> getTotalBoardInfoById(
      @PathVariable("boardId") Long boardId) throws Exception {
    RequestTotalBoardInfo result = new RequestTotalBoardInfo();
    result.setBoardInfo(boardService.getBoardInfoById(boardId));
    result.setTotalCommentCount(commentService.getCommentCountByBoardId(boardId));
    result.setCommentList(commentService.getCommentByBoardId(boardId));

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  /*
   * =================================== POST ==================================
   */
  @PostMapping("/board/new")
  public Long saveBoardInfo(BoardDto boardDto) {
    return boardService.saveBoard(boardDto);
  }

  @DeleteMapping("/board/{boardId}")
  public ResponseEntity<String> deleteBoardInfoById(@PathVariable("boardId") Long boardId)
      throws JpaException {
    boardService.deleteBoard(boardId);

    return new ResponseEntity<>("Success to delete board information", HttpStatus.OK);
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
