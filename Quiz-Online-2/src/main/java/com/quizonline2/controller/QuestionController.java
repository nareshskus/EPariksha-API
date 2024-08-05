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

import com.quizonline2.exceptions.CategoryNotFoundException;
import com.quizonline2.exceptions.QuestionNotFoundException;
import com.quizonline2.model.Question;
import com.quizonline2.model.DTO.QuestionDto;
import com.quizonline2.service.QuestionService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

	private final QuestionService questionService;
	
	@PostMapping("/addQuestion")
	public ResponseEntity<Question> addQuestion(@RequestBody QuestionDto questionDto){
		try {
			Question createdQuestion = questionService.createQuestion(questionDto);
			return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
		} catch (CategoryNotFoundException e) {
			throw new CategoryNotFoundException("Category not found: "+questionDto.getCategoryName());
		}
	}
	
	@GetMapping("/allQuestions")
	public ResponseEntity<List<Question>> getAllQuestions(){
		List<Question> question = questionService.getAllQuestions();
		return new ResponseEntity<>(question, HttpStatus.OK);
	}
	
	@GetMapping("/getQuestion/{questionId}")
	public ResponseEntity<Question> getQuestionById(@PathVariable long questionId){
		try {
			Question question = questionService.getQuestionById(questionId);
			return new ResponseEntity<>(question, HttpStatus.OK);
		} catch (QuestionNotFoundException e) {
			throw new QuestionNotFoundException("Question not found with id: "+questionId);
		}
	}
	
	@GetMapping("/byCategory/{categoryName}")
	public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String categoryName){
		try {
			List<Question> questions = questionService.getQuestionsByCategory(categoryName);
			return new ResponseEntity<>(questions, HttpStatus.OK);
		} catch (CategoryNotFoundException e) {
			throw new CategoryNotFoundException("Category not found with name: "+categoryName);
		}
	}
	
	@PutMapping("/updateQuestion/{questionId}")
	public ResponseEntity<Question> updateQuestion(@PathVariable long questionId, @RequestBody QuestionDto questionDto){
		try {
			Question updatedQuestion = questionService.updateQuestion(questionId, questionDto);
			return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
		} catch (CategoryNotFoundException e) {
			throw new CategoryNotFoundException("Category not found: "+questionDto.getCategoryName());
		}
	}
	
	@DeleteMapping("/deleteQuestion/{questionId}")
	public ResponseEntity<Void> deleteQuestion (@PathVariable long questionId){
		try {
			questionService.deleteQuestion(questionId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (QuestionNotFoundException e) {
			throw new QuestionNotFoundException("Question not found with id: "+questionId);
		}
	}
	
}
