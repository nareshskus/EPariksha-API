package com.quizonline2.service;

import java.util.List;

import com.quizonline2.model.Exam;
import com.quizonline2.model.ExamResult;
import com.quizonline2.model.Question;
import com.quizonline2.model.DTO.QuestionForExamDto;

public interface ExamAttemptService {

	public List<Exam> getAllExams(Long studentId);
	
	public Exam getExamById(Long examId);
	
	public List<QuestionForExamDto> getQuestionsForExam(Long examId);
	
	public ExamResult startExam(Long examId, String userName);
	
	public void saveQuestionResponse(Long examResultId, Long questionId, List<String> selectedOptions, String descriptiveAnswer);
	
	public void submitExam(Long examResultId);
	
}
