package com.quizonline2.serviceImpl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quizonline2.exceptions.ExamResultNotFoundException;
import com.quizonline2.model.ExamResult;
import com.quizonline2.model.QuestionResponse;
import com.quizonline2.repository.ExamResultRepository;
import com.quizonline2.repository.QuestionResponseRepository;
import com.quizonline2.service.ResultService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

	private final ExamResultRepository examResultRepository;
	private final QuestionResponseRepository questionResponseRepository;

	@Override
	public List<ExamResult> getAllExamResults() {
		return examResultRepository.findAll();
	}

	@Override
	public List<ExamResult> getResultByStudentId(Long studentId) {
		List<ExamResult> results = examResultRepository.findAllByStudentStudentIdAndCompletedIsTrue(studentId);
		return results;
	}

	@Override
	public List<QuestionResponse> getQuestionResponses(Long examResultId) {
		return questionResponseRepository.findAllByExamResultExamResultId(examResultId);
	}

	@Override
	public List<ExamResult> getExamResults(Long examId) {
		List<ExamResult> examResults = examResultRepository.findByExamExamId(examId);
		if(examResults== null)
			throw new ExamResultNotFoundException("Exam Result not found for exam id: "+examId);
		return examResults;
	}

}
