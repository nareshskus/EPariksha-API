package com.quizonline2.service;

import java.util.List;

import com.quizonline2.model.Exam;
import com.quizonline2.model.ExamResult;
import com.quizonline2.model.QuestionResponse;

public interface ResultService {

	public List<ExamResult> getAllExamResults();
	
	public List<ExamResult> getResultByStudentId(Long studentId);
	
	public List<QuestionResponse> getQuestionResponses(Long examResultId);
	
	public List<ExamResult> getExamResults(Long examId);
}
