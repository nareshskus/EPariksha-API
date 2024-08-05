package com.quizonline2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quizonline2.exceptions.ExamNotFoundException;
import com.quizonline2.model.*;
import com.quizonline2.model.DTO.ExamQuestionMappingDto;
import com.quizonline2.service.ExamQuestionService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/examQuestion")
@RequiredArgsConstructor
public class ExamQuestionController {

	private final ExamQuestionService examQuestionService;
	
	@PostMapping("/addQuestionsTo/{examId}")
	public ResponseEntity<Exam> addQuestionsToExam(@PathVariable Long examId, @RequestBody ExamQuestionMappingDto ExamQuestionMappingDto){
		try {
			Exam exam = examQuestionService.addQuestionsToExam(examId, ExamQuestionMappingDto.getQuestionIds());
			return new ResponseEntity<>(exam, HttpStatus.OK);
		} catch (ExamNotFoundException e) {
			throw new ExamNotFoundException("Exam not found with id: "+examId);
		}
	}
	
	@GetMapping("/allQuestionsOf/{examId}")
	public ResponseEntity<List<Question>> getQuestionsFromExam(@PathVariable Long examId){
		try {
			List<Question> questions = examQuestionService.getQuestionFromExam(examId);
			return ResponseEntity.ok(questions);
		} catch (ExamNotFoundException e) {
			throw new ExamNotFoundException("Exam not found with id: "+examId);
		}
	}
	
	@PutMapping("/updateQuestionsIn/{examId}")
	public ResponseEntity<Exam> updateQuestionsInExam(@PathVariable Long examId, @RequestBody ExamQuestionMappingDto examQuestionMappingDto){
		try {
			Exam updatedExam = examQuestionService.updateQuestionInExam(examId, examQuestionMappingDto.getQuestionIds());
			return new ResponseEntity<> (updatedExam, HttpStatus.OK);
		} catch (ExamNotFoundException e) {
			throw new ExamNotFoundException("Exam not found with id: "+examId);
		}
	}
	
	@DeleteMapping("/deleteQuestionFrom/{examId}/qId={questionId}")
	public ResponseEntity<String> deleteQuestionFromExam(@PathVariable Long examId, @PathVariable Long questionId){
		examQuestionService.deleteQuestionFromExam(examId, questionId);
		return ResponseEntity.ok("Question removed from exam successfully.");
	}
}
