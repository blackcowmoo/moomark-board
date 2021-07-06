package com.moomark.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moomark.board.domain.Board;
import com.moomark.board.domain.BoardDto;
import com.moomark.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

	private final BoardRepository boardRepository;
	
	
	public Long upLoadBoard(Board board) {
		return boardRepository.save(board).getId();
	}
	
	public void deleteBoard(Long boardId) {
		var board = boardRepository.findById(boardId).orElseThrow();
		boardRepository.delete(board);
	}
	
	public Board getBoardInfoById(Long id) {
		return boardRepository.getById(id);
	}
	
	public List<BoardDto> getBoardInfoByTitle(String title) {
		var boardList = boardRepository.findByTitle(title);
		List<BoardDto> boardDtoList = new ArrayList<>();
		for(Board board : boardList) {
			var boardDto = BoardDto.builder()
					.id(board.getId())
					.title(board.getTitle())
					.authorId(board.getAuthorId())
					.content(board.getContent())
					.uploadTime(board.getUploadTime())
					.viewsCount(board.getViewsCount())
					.recommendCount(board.getRecommendCount())
					.build();
			boardDtoList.add(boardDto);
			
		}
		
		return boardDtoList;
	}
}
