package com.moomark.post.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TagDto {
	private Long id;
	private String information;
}
