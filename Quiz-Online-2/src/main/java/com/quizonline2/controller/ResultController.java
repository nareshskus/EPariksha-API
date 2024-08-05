package com.quizonline2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quizonline2.model.ExamResult;
import com.quizonline2.model.QuestionResponse;
import com.quizonline2.service.ResultService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/result")
@RequiredArgsConstructor
public class ResultController {
	
	public final ResultService resultService;
	
	@GetMapping("/allResults")
	public ResponseEntity<List<ExamResult>> getExamResults(){
		List<ExamResult> examResults = resultService.getAllExamResults();
		return new ResponseEntity<>(examResults, HttpStatus.OK);
	}
	
	@GetMapping("/student/{studentId}")
	public ResponseEntity<List<ExamResult>> getResultOfStudent(@PathVariable Long studentId){
		List<ExamResult> examResults = resultService.getResultByStudentId(studentId);
		return new ResponseEntity<>(examResults, HttpStatus.OK);
	}
	
	@GetMapping("/responsesExamResultId/{examResultId}")
	public ResponseEntity<List<QuestionResponse>> getResponsesByResultId(@PathVariable Long examResultId){
		List<QuestionResponse> questionResponses = resultService.getQuestionResponses(examResultId);
		return new ResponseEntity<>(questionResponses, HttpStatus.OK);
	}
	
	@GetMapping("/resultOfExam/{examId}")
	public ResponseEntity<List<ExamResult>> getResultOfExam(@PathVariable Long examId){
		List<ExamResult> examResults = resultService.getExamResults(examId);
		return new ResponseEntity<>(examResults, HttpStatus.OK);
	}
}
