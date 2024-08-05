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
import com.quizonline2.model.Exam;
import com.quizonline2.model.DTO.ExamDto;
import com.quizonline2.service.ExamService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/exams")
@RequiredArgsConstructor
public class ExamController {

	private final ExamService examService;
	
	@PostMapping("/createExam")
	public ResponseEntity<Exam> createExam(@RequestBody ExamDto examDto){
		Exam exam = examService.createExam(examDto);
		return new ResponseEntity<>(exam, HttpStatus.CREATED);
	}
	
	@GetMapping("/allExams")
	public ResponseEntity<List<Exam>> getAllExams(){
		List<Exam> exams =  examService.getAllExams(); 
		return new ResponseEntity<>(exams, HttpStatus.OK);
	}
	
	@GetMapping("/getExam/{examId}")
	public ResponseEntity<Exam> getExamById(@PathVariable long examId){
		try {
			Exam exam = examService.getExamById(examId);
			return new ResponseEntity<>(exam, HttpStatus.OK);
		} catch (ExamNotFoundException e) {
			throw new ExamNotFoundException("Exam not found with id: "+examId);
		}
	}
	
	@PutMapping("/updateExam/{examId}")
	public ResponseEntity<Exam> updateExam(@PathVariable long examId, @RequestBody ExamDto examDto){
		try {
			Exam updatedExam = examService.updateExam(examId, examDto);
			return new ResponseEntity<>(updatedExam, HttpStatus.OK);
		} catch (ExamNotFoundException e) {
			throw new ExamNotFoundException("Exam not found with id: "+examId);
		}
	}
	
	@DeleteMapping("/deleteExam/{examId}")
	public ResponseEntity<Void> deleteExam(@PathVariable long examId){
		try {
			examService.deleteExam(examId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ExamNotFoundException e) {
			throw new ExamNotFoundException("Exam not found with id: "+examId);
		}
	}
}
