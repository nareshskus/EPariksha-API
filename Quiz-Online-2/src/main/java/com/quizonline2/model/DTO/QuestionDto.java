package com.quizonline2.model.DTO;

import java.util.List;

import com.quizonline2.enums.QuestionType;

public class QuestionDto {

	private String description;
	private List<String> options;
	private List<String> correctOptions;
	private QuestionType questionType;
	private String categoryName;
	public QuestionDto(String description, List<String> options, List<String> correctOptions, QuestionType questionType,
			String categoryName) {
		super();
		this.description = description;
		this.options = options;
		this.correctOptions = correctOptions;
		this.questionType = questionType;
		this.categoryName = categoryName;
	}
	public QuestionDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getOptions() {
		return options;
	}
	public void setOptions(List<String> options) {
		this.options = options;
	}
	public List<String> getCorrectOptions() {
		return correctOptions;
	}
	public void setCorrectOptions(List<String> correctOptions) {
		this.correctOptions = correctOptions;
	}
	public QuestionType getQuestionType() {
		return questionType;
	}
	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
}
