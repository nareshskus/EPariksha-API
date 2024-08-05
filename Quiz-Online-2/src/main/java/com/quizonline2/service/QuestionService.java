package com.quizonline2.service;

import java.util.List;

import com.quizonline2.model.Question;
import com.quizonline2.model.DTO.QuestionDto;

public interface QuestionService {

	Question createQuestion(QuestionDto questionDto);
	Question getQuestionById(Long id);
	List<Question> getAllQuestions();
	Question updateQuestion(Long id, QuestionDto questionDto);
	void deleteQuestion(Long id);
	List<Question> getQuestionsByCategory(String categoryName);
}
