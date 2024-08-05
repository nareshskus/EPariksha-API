package com.quizonline2.service;

import java.util.List;

import com.quizonline2.model.Exam;
import com.quizonline2.model.Question;
import com.quizonline2.model.DTO.ExamDto;
import com.quizonline2.model.DTO.QuestionDto;

public interface ExamService {

	Exam createExam(ExamDto examDto);
	
	List<Exam> getAllExams();
	
	Exam getExamById(Long id);
	
	String deleteExam(long id);
	
	Exam updateExam(long id, ExamDto examDto);
}
