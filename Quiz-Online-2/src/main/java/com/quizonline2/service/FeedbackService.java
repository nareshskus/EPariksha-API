package com.quizonline2.service;

import java.util.List;

import com.quizonline2.model.Feedback;
import com.quizonline2.model.DTO.FeedbackDto;

public interface FeedbackService {

	Feedback newFeedback (FeedbackDto feedbackDto);
	public List<Feedback> getAllFeedbacks();
}
