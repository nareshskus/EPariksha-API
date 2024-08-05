package com.quizonline2.model.DTO;

import java.util.List;

public class ExamQuestionMappingDto {

	private List<Long> questionIds;

	public ExamQuestionMappingDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExamQuestionMappingDto(List<Long> questionIds) {
		super();
		this.questionIds = questionIds;
	}

	public List<Long> getQuestionIds() {
		return questionIds;
	}

	public void setQuestionIds(List<Long> questionIds) {
		this.questionIds = questionIds;
	}
	
	
}
