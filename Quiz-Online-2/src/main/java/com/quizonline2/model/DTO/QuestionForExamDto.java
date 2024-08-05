package com.quizonline2.model.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForExamDto {

	Long questionId;
	String description;
	List<String> options;
	String question_type;
	String categoryName;
}
