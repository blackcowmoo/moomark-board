package com.moomark.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	private Long id;
	
	@Builder.Default
	private Long parentsId = (long) 0;
	
	private String categoryType;
}
