package com.quizonline2.serviceImpl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.quizonline2.exceptions.ExamNotFoundException;
import com.quizonline2.model.Exam;
import com.quizonline2.model.ExamQuestionMapping;
import com.quizonline2.model.Question;
import com.quizonline2.repository.ExamQuestionRepository;
import com.quizonline2.repository.ExamRepository;
import com.quizonline2.repository.QuestionRepository;
import com.quizonline2.service.ExamQuestionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ExamQuestionServiceImpl implements ExamQuestionService {

	private final ExamRepository examRepository;
	private final QuestionRepository questionRepository;
	private final ExamQuestionRepository examQuestionRepository;
	@Override
	public Exam addQuestionsToExam(Long examId, List<Long> questionIds) {
		Exam exam = examRepository.findById(examId)
				.orElseThrow(()-> new ExamNotFoundException("Exam not found with id: "+examId));
		List<Question> questions= questionRepository.findAllById(questionIds);
		for(Question question : questions) {
			ExamQuestionMapping mapping= new ExamQuestionMapping();
			mapping.setExam(exam);
			mapping.setQuestion(question);
			exam.getExamQuestionMappings().add(mapping);
		}
		return examRepository.save(exam);
	}

	@Override
	public List<Question> getQuestionFromExam(Long examId) {
		Exam exam = examRepository.findById(examId)
				.orElseThrow(()-> new ExamNotFoundException("Exam not found with id: "+examId));
		List<ExamQuestionMapping> questionMappings = exam.getExamQuestionMappings();
		
		List<Question> questions = questionMappings.stream()
				.map(ExamQuestionMapping::getQuestion)
				.collect(Collectors.toList());
		
		return questions;
	}

	@Override
	public Exam updateQuestionInExam(Long examId, List<Long> newQuestionIds) {
		Exam exam = examRepository.findById(examId)
				.orElseThrow(()-> new ExamNotFoundException("Exam not found with id: "+examId));
		
		List<Question> newQuestions = questionRepository.findAllById(newQuestionIds);
		exam.getExamQuestionMappings().clear();
		
		for(Question question : newQuestions) {
			ExamQuestionMapping mapping= new ExamQuestionMapping();
			mapping.setExam(exam);
			mapping.setQuestion(question);
			exam.getExamQuestionMappings().add(mapping);
		}
		
		return examRepository.save(exam);
	}
	
	@Override
	public void deleteQuestionFromExam(Long examId, Long questionId) {
		examQuestionRepository.deleteQuestionInExam(examId, questionId);
	}

	
}
