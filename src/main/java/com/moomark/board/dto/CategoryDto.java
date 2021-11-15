package com.moomark.board.dto;

import com.moomark.board.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto implements DtoInterface<Category>{
    private Long id;
    
    @Builder.Default
    private Long parentsId = (long) 0;
    
    private String categoryType;

    @Override
    public Category toEntity() {
      return Category.builder()
          .type(this.categoryType)
          .build();
    }
}
