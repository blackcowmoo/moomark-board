package com.moomark.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moomark.board.domain.Board;
import com.moomark.board.domain.BoardComment;
import com.moomark.board.domain.Comment;
import com.moomark.board.domain.CommentDto;
import com.moomark.board.repository.BoardCommentRepository;
import com.moomark.board.repository.BoardRepository;
import com.moomark.board.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
	private final BoardRepository boardRepository;
	private final CommentRepository commentRepository;
	private final BoardCommentRepository boardCommentRepository;
	
	/**
	 * 댓글 정보 조회
	 * @param boardId
	 * @return
	 * @throws Exception
	 */
	public List<CommentDto> getCommentByBoardId(Long boardId) throws Exception {
		Board board = boardRepository.findById(boardId).orElseThrow(
				() -> new Exception("작성된 게시글이 없습니다."));
		
		List<BoardComment> boardCommentList = boardCommentRepository.findByBoard(board);
		List<CommentDto> resultList = new ArrayList<>();
		for(BoardComment boardComment : boardCommentList) {
			Comment comment = boardComment.getComment();
			CommentDto commentDto = CommentDto.builder()
					.id(comment.getId())
					.content(comment.getContent())
					.parentsId(comment.getParentId())
					.build();
			resultList.add(commentDto);
		}
		return resultList;
	}
	
	/**
	 * 게시글 번호 기반의 댓글 수 반환
	 * @param boardId
	 * @return
	 * @throws Exception
	 */
	public int getCommentCountByBoardId(Long boardId) throws Exception{
		Board board = boardRepository.findById(boardId).orElseThrow(
				() -> new Exception("조회를 하고자 하는 게시글이 없습니다."));
		
		return boardCommentRepository.findByBoard(board).size();
	}
	
	/**
	 * Comment 저장 함수
	 * @return
	 */
	public Long saveComment(CommentDto commentDto) {
		Comment comment = Comment.builder()
				.content(commentDto.getContent())
				.userId(commentDto.getUserId())
				.build();
		return commentRepository.save(comment).getId();
	}
	
	/**
	 * Comment 삭제 함수
	 * @param id
	 * @return
	 */
	public Boolean deleteComment(Long id) {
		try {
			var comment = commentRepository.findById(id).orElseThrow(()
					-> new Exception("찾는 댓글 정보가 없습니다."));
			commentRepository.deleteById(comment.getId());
			return true;
		} catch (Exception e) {
			log.error("delete error {}", e);
			return false;
		}
	}
}
