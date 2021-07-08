package com.moomark.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moomark.board.domain.Board;
import com.moomark.board.domain.BoardCategory;
import com.moomark.board.domain.BoardDto;
import com.moomark.board.repository.BoardCategoryRepository;
import com.moomark.board.repository.BoardRepository;
import com.moomark.board.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

	private final BoardRepository boardRepository;
	private final CategoryRepository categoryRepository;
	private final BoardCategoryRepository boardCategoryRepository;
	
	public Long addBoard(BoardDto boardDto) {
		log.info("add Board : {}", boardDto);
		
		var board = Board.builder()
				.authorId(boardDto.getAuthorId())
				.content(boardDto.getContent())
				.build();
		return boardRepository.save(board).getId();
	}
	
	public void deleteBoard(Long boardId) {
		var board = boardRepository.findById(boardId).orElseThrow();
		boardRepository.delete(board);
	}
	
	public Board getBoardInfoById(Long id) {
		return boardRepository.getById(id);
	}
	
	/**
	 * Title 기반 Board 리스트 가져오기
	 * @param title
	 * @return
	 */
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
	
	/**
	 * Board에 Category 추가
	 * @param boardId
	 * @param categoryId
	 */
	public void addCategoryToBoard(Long boardId, Long categoryId) {
		var board = boardRepository.findById(boardId).orElseThrow();
		var category = categoryRepository.findById(categoryId).orElseThrow();
		
		boardCategoryRepository.save(BoardCategory.builder()
				.board(board)
				.category(category)
				.build());
	}
	
	/**
	 * Board에서 Category 제거
	 * @param boardId
	 * @param categoryId
	 */
	public void deleteCategoryToBoard(Long boardId, Long categoryId) {
		var board = boardRepository.findById(boardId).orElseThrow();
		var category = categoryRepository.findById(categoryId).orElseThrow();

		var boardCategory = boardCategoryRepository.findByBoardAndCategory(board, category);
		boardCategoryRepository.delete(boardCategory);
	}
}
