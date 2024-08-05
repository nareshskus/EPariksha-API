package com.quizonline2.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ExamResult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long examResultId;
	
	@ManyToOne
	@JoinColumn(name = "exam_id")
	private Exam exam;
	
	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;
	
	private Double obtainedMarks;
	
	private Double score;
	
	private boolean completed;
	
	@JsonIgnore
	@OneToMany(mappedBy = "examResult", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QuestionResponse> questionResponses;
}
