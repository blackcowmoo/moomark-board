package com.moomark.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moomark.board.domain.Comment;
import com.moomark.board.domain.CommentDto;
import com.moomark.board.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
	private final CommentRepository commentRepository;
	
	/**
	 * 댓글 정보 조회
	 * @param boardId
	 * @return
	 * @throws Exception
	 */
	public CommentDto getComment(Long boardId) throws Exception {
		var comment = commentRepository.findById(boardId).orElseThrow(
				() -> new Exception("댓글을 불러오지 못했습니다."));
		return CommentDto.builder()
				.boardId(comment.getId())
				.userId(comment.getUserId())
				.content(comment.getContent())
				.parentsId(comment.getParentId())
				.childIdList(comment.getChildIdList())
				.build();
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
