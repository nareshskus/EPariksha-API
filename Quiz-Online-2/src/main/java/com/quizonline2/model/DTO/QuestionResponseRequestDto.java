package com.quizonline2.model.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionResponseRequestDto {

	private Long questionId;
	private List<String> selectedOptions;
	private String descriptiveAnswer;
}
