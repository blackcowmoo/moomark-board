package com.moomark.board.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.moomark.board.domain.BoardDto;
import com.moomark.board.service.BoardService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	/* Static class */
	@Data
	static class RequestBoardInfo {
		private Long authorId;
		
		private String title;
		
		private String content;
	}
	
	@Data
	static class RequestAddCategory {
		private Long boardId;
		
		private Long cateogyId;
	}
	
	/* Get */
	@GetMapping("/board/{boardId}/content")
	public BoardDto getBoardInfoById(@PathVariable("boardId") Long boardId) throws Exception {
		return boardService.getBoardInfoById(boardId);
	}
	
	/* Post */
	@PostMapping("/board/new")
	public Long saveBoardInfo(@RequestBody RequestBoardInfo requestBoardInfo) {
		
		return boardService.saveBoard(BoardDto.builder()
				.authorId(requestBoardInfo.authorId)
				.title(requestBoardInfo.title)
				.content(requestBoardInfo.content)
				.build());
	}
	
	@PostMapping("/board/category")
	public void addCategoryToBoard(@RequestBody RequestAddCategory request) throws Exception {
		boardService.addCategoryToBoard(request.boardId, request.cateogyId);
	}
	
	/* Put */
	@PutMapping("/board/content")
	public Long updateBoardInfo(@RequestBody RequestBoardInfo requestBoardInfo) throws Exception {
		
		return boardService.updateBoardInfo(BoardDto.builder()
				.authorId(requestBoardInfo.authorId)
				.title(requestBoardInfo.title)
				.content(requestBoardInfo.content)
				.build());
		
	}
	
	/* Delete */
	@DeleteMapping("/board/{boardId}")
	public void deleteBoardInfoById(@PathVariable("boardId") Long boardId) {
		boardService.deleteBoard(boardId);
	}
}
