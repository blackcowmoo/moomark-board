package com.moomark.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moomark.board.domain.Board;
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
	
	// TODO : 게시판 번호를 기반으로 모든 댓글 가져오기 구현
	/**
	 * 댓글 정보 조회
	 * @param boardId
	 * @return
	 * @throws Exception
	 */
	public CommentDto getCommentByBoardId(Long boardId) throws Exception {
		Board board = boardRepository.findById(boardId).orElseThrow(
				() -> new Exception("작성된 게시글이 없습니다."));
		
		boardCommentRepository.findByBoard(board);
		return null;
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
