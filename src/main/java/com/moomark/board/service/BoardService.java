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
	 * 
	 * @param boardDto
	 * @return
	 */
	@Transactional
	public Long saveBoard(BoardDto boardDto) {
		log.info("add Board : {}", boardDto);

		var board = Board.builder()
				.title(boardDto.getTitle())
				.authorId(boardDto.getAuthorId())
				.content(boardDto.getContent())
				.build();

		return boardRepository.save(board).getId();
	}

	/**
	 * 게시판 지우기 함수
	 * 
	 * @param boardId
	 */
	@Transactional
	public void deleteBoard(Long boardId) {
		var board = boardRepository.findById(boardId).orElseThrow();
		boardRepository.delete(board);
	}

	/**
	 * board id 기반 Board 정보 조회
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public BoardDto getBoardInfoById(Long id) throws Exception {
		Board getData = boardRepository.findById(id).orElseThrow(()
				-> new Exception("조회하고자 하는 게시글이 없습니다. 다시 확인해주세요."));
		getData.upCountViewCount();
		List<CategoryDto> categories = new ArrayList<>();
		for (BoardCategory boardCategory : getData.getBoardCategory()) {
			var category = CategoryDto.builder().id(boardCategory.getCategory().getId())
					.categoryType(boardCategory.getCategory().getCategoryType())
					.parentsId(boardCategory.getCategory().getParentAfterNullCheck()).build();

			categories.add(category);
		}

		return BoardDto.builder()
				.title(getData.getTitle())
				.authorId(getData.getAuthorId())
				.id(getData.getId())
				.viewsCount(getData.getViewsCount())
				.recommendCount(getData.getRecommendCount())
				.uploadTime(getData.getUploadTime())
				.content(getData.getContent())
				.categories(categories)
				.build();
	}

	/**
	 * Title 기반 Board 리스트 가져오기
	 * 
	 * @param title
	 * @return
	 */
	public List<BoardDto> getBoardInfoByTitle(String title) {
		var boardList = boardRepository.findByTitle(title);
		List<BoardDto> boardDtoList = new ArrayList<>();
		for (Board board : boardList) {
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
	 * 
	 * @param boardId
	 * @param categoryId
	 * @throws Exception 
	 */
	@Transactional
	public void addCategoryToBoard(Long boardId, Long categoryId) throws Exception {
		var board = boardRepository.findById(boardId).orElseThrow(() 
				-> new Exception("카테고리를 추가하고자 하는 게시글을 찾을 수 없습니다."));
		var category = categoryRepository.findById(categoryId).orElseThrow(()
				-> new Exception("게시글에 추가하고자 하는 카테고리를 찾을 수 없습니다."));

		boardCategoryRepository.save(BoardCategory.builder()
				.board(board)
				.category(category)
				.build());
		
		log.info("Category info : {}", board.getBoardCategory().get(0).getCategory().getCategoryType());
	}

	/**
	 * Board에서 Category 제거
	 * 
	 * @param boardId
	 * @param categoryId
	 */
	@Transactional
	public void deleteCategoryToBoard(Long boardId, Long categoryId) {
		var board = boardRepository.findById(boardId).orElseThrow();
		var category = categoryRepository.findById(categoryId).orElseThrow();

		var boardCategory = boardCategoryRepository.findByBoardAndCategory(board, category);
		boardCategoryRepository.delete(boardCategory);
	}

	/**
	 * CategoryID 기반 Board정보 조회
	 * 
	 * @param categoryId
	 * @return
	 */
	public List<BoardDto> getBoardListByCategoryId(Long categoryId) {
		List<BoardCategory> boardCategoryList = boardCategoryRepository
				.findByCategory(categoryRepository.getById(categoryId));

		List<BoardDto> resultList = new ArrayList<>();
		for (BoardCategory boardCategory : boardCategoryList) {
			var board = boardCategory.getBoard();
			resultList.add(BoardDto.builder()
					.title(board.getTitle())
					.authorId(board.getAuthorId())
					.id(board.getId())
					.content(board.getContent())
					.build());
		}

		return resultList;
	}
	
	/**
	 * 게시글 업데이트 함수
	 * @param boardDto
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public Long updateBoardInfo(BoardDto boardDto) throws Exception {
		var getData = boardRepository.findById(boardDto.getId()).orElseThrow(()
				-> new Exception("변경하고자 하는 게시글을 찾을 수 없습니다."));
		
		getData.updateInformation(boardDto.getTitle(), boardDto.getContent());
		return getData.getId();
	}
}
