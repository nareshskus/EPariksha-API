package com.quizonline2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quizonline2.model.Feedback;
import com.quizonline2.model.DTO.FeedbackDto;
import com.quizonline2.service.FeedbackService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

	private final FeedbackService feedbackService;
	
	@PostMapping("/addFeedback")
	public ResponseEntity<Feedback> addFeedback(@RequestBody FeedbackDto feedbackDto){
		Feedback createdFeedback = feedbackService.newFeedback(feedbackDto);
		return new ResponseEntity<>(createdFeedback, HttpStatus.OK);
	}
	
	@GetMapping("/allFeedbacks")
	public ResponseEntity<List<Feedback>> allFeedbacks(){
		List<Feedback> allFeedbacks = feedbackService.getAllFeedbacks();
		return new ResponseEntity<>(allFeedbacks, HttpStatus.OK);
	}
}
