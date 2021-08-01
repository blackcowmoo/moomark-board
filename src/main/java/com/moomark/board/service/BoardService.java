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
	 * �Խ��� ���� �Լ�
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
	 * �Խ��� ����� �Լ�
	 * 
	 * @param boardId
	 */
	@Transactional
	public void deleteBoard(Long boardId) {
		var board = boardRepository.findById(boardId).orElseThrow();
		boardRepository.delete(board);
	}

	/**
	 * board id ��� Board ���� ��ȸ
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public BoardDto getBoardInfoById(Long id) throws Exception {
		Board getData = boardRepository.findById(id).orElseThrow(()
				-> new Exception("��ȸ�ϰ��� �ϴ� �Խñ��� �����ϴ�. �ٽ� Ȯ�����ּ���."));
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
	 * Title ��� Board ����Ʈ ��������
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
	 * Board�� Category �߰�
	 * 
	 * @param boardId
	 * @param categoryId
	 * @throws Exception 
	 */
	@Transactional
	public void addCategoryToBoard(Long boardId, Long categoryId) throws Exception {
		var board = boardRepository.findById(boardId).orElseThrow(() 
				-> new Exception("ī�װ��� �߰��ϰ��� �ϴ� �Խñ��� ã�� �� �����ϴ�."));
		var category = categoryRepository.findById(categoryId).orElseThrow(()
				-> new Exception("�Խñۿ� �߰��ϰ��� �ϴ� ī�װ��� ã�� �� �����ϴ�."));

		boardCategoryRepository.save(BoardCategory.builder()
				.board(board)
				.category(category)
				.build());
		
		log.info("Category info : {}", board.getBoardCategory().get(0).getCategory().getCategoryType());
	}

	/**
	 * Board���� Category ����
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
	 * CategoryID ��� Board���� ��ȸ
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
	 * �Խñ� ������Ʈ �Լ�
	 * @param boardDto
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public Long updateBoardInfo(BoardDto boardDto) throws Exception {
		var getData = boardRepository.findById(boardDto.getId()).orElseThrow(()
				-> new Exception("�����ϰ��� �ϴ� �Խñ��� ã�� �� �����ϴ�."));
		
		getData.updateInformation(boardDto.getTitle(), boardDto.getContent());
		return getData.getId();
	}
}
