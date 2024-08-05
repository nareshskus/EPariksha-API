package com.quizonline2.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Exam {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long examId;
	
	private String examName;
	private LocalDate examDate;
	private boolean isActive;
	private int maxMarks;
	private int numberQuestions;
	private int durationMinutes;
	
	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime startTime;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "exam", orphanRemoval = true)
	private List<ExamQuestionMapping> examQuestionMappings;
	
//	@JsonIgnore
//	@OneToMany(cascade = CascadeType.ALL)
//	private ExamResult examResult;
	
}
