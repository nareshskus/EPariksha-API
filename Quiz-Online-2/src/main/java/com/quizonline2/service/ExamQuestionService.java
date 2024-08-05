package com.quizonline2.service;

import java.util.List;

import com.quizonline2.model.Exam;
import com.quizonline2.model.Question;

public interface ExamQuestionService {

	public Exam addQuestionsToExam(Long examId, List<Long> questionIds);
	
	public List<Question> getQuestionFromExam(Long examId);
	
	public Exam updateQuestionInExam(Long examId, List<Long> questionIds);
	
	public void deleteQuestionFromExam(Long examId, Long questionId);
}
