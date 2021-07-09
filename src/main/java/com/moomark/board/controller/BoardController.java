package com.moomark.board.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moomark.board.domain.BoardDto;
import com.moomark.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	
	@GetMapping("/board/{boardId}/content")
	public BoardDto getBoardInfoById(@PathVariable("boardId") Long boardId) {
		return boardService.getBoardInfoById(boardId);
	}
	
	@PutMapping("/board/new")
	public Long putBoardInfo(BoardDto boardDto) {
		return boardService.saveBoard(boardDto);
	}
	
	@DeleteMapping("/board/{boardId}")
	public void deleteBoardInfoById(@PathVariable("boardId") Long boardId) {
		boardService.deleteBoard(boardId);
	}
}
