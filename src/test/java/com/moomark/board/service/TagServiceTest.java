package com.moomark.board.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.moomark.board.dto.TagDto;
import com.moomark.board.exception.JpaException;
import com.moomark.board.repository.TagRepository;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
  @Mock
  private TagRepository tagRepository;
  @InjectMocks
  private TagService tagService;

  @Test
  @DisplayName("tag찾기 기능 테스트")
  void findTagTest() throws JpaException {
    TagDto tagDto = new TagDto(1L, "TEST");
    when(tagRepository.save(any())).thenReturn(tagDto.toEntity());
    tagService.saveTag(tagDto);
    when(tagRepository.findById(1L)).thenReturn(Optional.of(tagDto.toEntity()));

    TagDto responseDto = tagService.findTagById(tagDto.getId());

    Assertions.assertThat(responseDto).isNotNull();
    Assertions.assertThat(responseDto.getInformation()).isEqualTo(tagDto.getInformation());
  }

}
