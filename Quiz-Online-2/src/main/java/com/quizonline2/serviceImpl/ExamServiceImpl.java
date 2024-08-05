package com.quizonline2.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.quizonline2.exceptions.ExamNotFoundException;
import com.quizonline2.model.Exam;
import com.quizonline2.model.ExamQuestionMapping;
import com.quizonline2.model.Question;
import com.quizonline2.model.DTO.ExamDto;
import com.quizonline2.model.DTO.QuestionDto;
import com.quizonline2.repository.ExamQuestionRepository;
import com.quizonline2.repository.ExamRepository;
import com.quizonline2.repository.QuestionRepository;
import com.quizonline2.service.ExamService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

	private final ExamRepository examRepository;
	private final ExamQuestionRepository examQuestionRepository;
	
	@Override
	public Exam createExam(ExamDto examDto) {
		Exam exam = new Exam();
		BeanUtils.copyProperties(examDto, exam);
		return examRepository.save(exam);
	}

	@Override
	public List<Exam> getAllExams() {
		return examRepository.findAll();
	}

	@Override
	public Exam getExamById(Long examId) {
		return examRepository.findById(examId)
				.orElseThrow(()-> new ExamNotFoundException("Exam not found with id: "+examId));
	}

	@Override
	public String deleteExam(long examId) {
		if(examRepository.existsById(examId)) {
			examQuestionRepository.deleteExam(examId);
			examRepository.deleteById(examId);
			return "Exam deleted successfully!!!";
		} else {
			throw new ExamNotFoundException("Exam not found with id: "+examId);
		}
	}

	@Override
	public Exam updateExam(long examId, ExamDto examDto) {
		if(examRepository.existsById(examId)) {
			Exam existingExam = getExamById(examId);
			BeanUtils.copyProperties(examDto, existingExam);
			return examRepository.save(existingExam);
		} else {
			throw new ExamNotFoundException("Exam not found with id: "+examId);
		}
	}
	

}
