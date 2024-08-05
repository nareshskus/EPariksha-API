package com.quizonline2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quizonline2.exceptions.ExamAlreadyAttemptedException;
import com.quizonline2.model.Exam;
import com.quizonline2.model.ExamResult;
import com.quizonline2.model.DTO.QuestionForExamDto;
import com.quizonline2.model.DTO.QuestionResponseRequestDto;
import com.quizonline2.model.DTO.StartExamRequestDto;
import com.quizonline2.service.ExamAttemptService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/studentExam")
@RequiredArgsConstructor
public class ExamAttemptController {
	
	private final ExamAttemptService examAttemptService;
	
	@PostMapping("/start/{examId}")
	public ResponseEntity<ExamResult> startExam(@PathVariable Long examId, @RequestBody StartExamRequestDto startExamRequestDto){
		try {
			String userName = startExamRequestDto.getUserName();
			ExamResult examResult = examAttemptService.startExam(examId, userName);
			
			if(examResult != null) {
				return new ResponseEntity<>(examResult, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (ExamAlreadyAttemptedException e) {
			throw new ExamAlreadyAttemptedException("Exam Already Attempted of exam Id: "+examId);
		}
	}
	
	@GetMapping("/{examId}/questions")
	public ResponseEntity<List<QuestionForExamDto>> getQuestionsForExam(@PathVariable Long examId){
		List<QuestionForExamDto> questions = examAttemptService.getQuestionsForExam(examId);
		return ResponseEntity.ok(questions);
	}
	
	@PostMapping("/submit/{examResultId}")
	public ResponseEntity<String> submitExam(@PathVariable Long examResultId, @RequestBody List<QuestionResponseRequestDto> responses){
		for(QuestionResponseRequestDto response: responses) {
			examAttemptService.saveQuestionResponse(examResultId, response.getQuestionId(), response.getSelectedOptions(), response.getDescriptiveAnswer());
		}
		
		examAttemptService.submitExam(examResultId);
		return new ResponseEntity<>("Exam Submitted Successfully", HttpStatus.OK);
	}
	
	@GetMapping("/allExams/{studentId}")
	public ResponseEntity<List<Exam>> getAllActiveExams(@PathVariable Long studentId){
		List<Exam> exams = examAttemptService.getAllExams(studentId);
		return new ResponseEntity<>(exams, HttpStatus.OK);
	}

}
