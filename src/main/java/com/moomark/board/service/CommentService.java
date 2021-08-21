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
	 * ��� ���� ��ȸ
	 * @param boardId
	 * @return
	 * @throws Exception
	 */
	public CommentDto getComment(Long boardId) throws Exception {
		var comment = commentRepository.findById(boardId).orElseThrow(
				() -> new Exception("����� �ҷ����� ���߽��ϴ�."));
		return CommentDto.builder()
				.boardId(comment.getId())
				.userId(comment.getUserId())
				.content(comment.getContent())
				.parentsId(comment.getParentId())
				.childIdList(comment.getChildIdList())
				.build();
	}
	
	/**
	 * Comment ���� �Լ�
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
	 * Comment ���� �Լ�
	 * @param id
	 * @return
	 */
	public Boolean deleteComment(Long id) {
		try {
			var comment = commentRepository.findById(id).orElseThrow(()
					-> new Exception("ã�� ��� ������ �����ϴ�."));
			commentRepository.deleteById(comment.getId());
			return true;
		} catch (Exception e) {
			log.error("delete error {}", e);
			return false;
		}
	}
}
