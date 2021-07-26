package com.moomark.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moomark.board.domain.Board;
import com.moomark.board.domain.BoardCategory;
import com.moomark.board.domain.BoardDto;
import com.moomark.board.domain.CategoryDto;
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
	
	/**
	 * 게시판 저장 함수
	 * @param boardDto
	 * @return
	 */
	public Long saveBoard(BoardDto boardDto) {
		log.info("add Board : {}", boardDto);
		
		var board = Board.builder()
				.title(boardDto.getTitle())
				.authorId(boardDto.getAuthorId())
				.content(boardDto.getContent())
				.viewsCount((long) 0)
				.recommedCount((long) 0)
				.build();
		
		return boardRepository.save(board).getId();
	}
	
	/**
	 * 게시판 지우기 함수
	 * @param boardId
	 */
	public void deleteBoard(Long boardId) {
		var board = boardRepository.findById(boardId).orElseThrow();
		boardRepository.delete(board);
	}
	
	/**
	 * board id 기반 Board 정보 조회
	 * @param id
	 * @return
	 */
	public BoardDto getBoardInfoById(Long id) {
			Board getData = boardRepository.getById(id);
			List<CategoryDto> categories = new ArrayList<>();
			for(BoardCategory boardCategory : getData.getBoardCategory()) {
				var category = new CategoryDto();
				category.setId(boardCategory.getCategory().getId());
				category.setCategoryType(boardCategory.getCategory().getCategoryType());
				if(boardCategory.getCategory().getParent() != null) {
					category.setParentsId(boardCategory.getCategory().getParent().getId());
				}
				categories.add(category);
			}
		return BoardDto.builder()
				.title(getData.getTitle())
				.authorId(getData.getAuthorId())
				.id(getData.getId())
				.content(getData.getContent())
				.categories(categories)
				.build();
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
	
	public List<BoardDto> getBoardListByCategoryId(Long categoryId) {
		List<BoardCategory> boardCategoryList = boardCategoryRepository.findByCategory(
				categoryRepository.getById(categoryId));
		
		List<BoardDto> resultList = new ArrayList<>();
		for(BoardCategory boardCategory : boardCategoryList) {
			var board = boardCategory.getBoard();
			resultList.add();
		}
	}
}
