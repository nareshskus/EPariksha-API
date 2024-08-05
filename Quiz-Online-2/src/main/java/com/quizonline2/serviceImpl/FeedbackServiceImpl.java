package com.quizonline2.serviceImpl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.quizonline2.exceptions.StudentNotFoundException;
import com.quizonline2.model.Feedback;
import com.quizonline2.model.Student;
import com.quizonline2.model.DTO.FeedbackDto;
import com.quizonline2.repository.FeedbackRepository;
import com.quizonline2.repository.StudentRepository;
import com.quizonline2.service.FeedbackService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

	private final FeedbackRepository feedbackRepository;
	private final StudentRepository studentRepository;
	
	@Override
	public Feedback newFeedback(FeedbackDto feedbackDto) {
		try {
			Feedback feedback = new Feedback();
			BeanUtils.copyProperties(feedbackDto, feedback);
			
			Student student = studentRepository.findById(feedbackDto.getStudentId()).orElseThrow(()->
					new StudentNotFoundException("Student not found with id: "+feedbackDto.getStudentId()));
			
			feedback.setStudent(student);
			return feedbackRepository.save(feedback);
		} catch (StudentNotFoundException e) {
			throw new StudentNotFoundException("Student not found with id: "+feedbackDto.getStudentId());
		}
	}

	@Override
	public List<Feedback> getAllFeedbacks() {
		return feedbackRepository.findAll();
	}

}
