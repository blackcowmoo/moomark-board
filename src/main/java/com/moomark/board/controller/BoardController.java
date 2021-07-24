package com.moomark.board.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moomark.board.domain.BoardDto;
import com.moomark.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	
	/*Get*/
	@GetMapping("/board/{boardId}/content")
	public BoardDto getBoardInfoById(@PathVariable("boardId") Long boardId) {
		return boardService.getBoardInfoById(boardId);
	}
	
	/*Post*/
	@PostMapping("/board/new")
	public Long saveBoardInfo(BoardDto boardDto) {
		return boardService.saveBoard(boardDto);
	}
	
	/*Delete*/
	@DeleteMapping("/board/{boardId}")
	public void deleteBoardInfoById(@PathVariable("boardId") Long boardId) {
		boardService.deleteBoard(boardId);
	}
}
