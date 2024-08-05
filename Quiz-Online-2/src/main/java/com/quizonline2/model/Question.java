package com.quizonline2.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quizonline2.enums.QuestionType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long questionId;
	
	private String description;
	
	@ElementCollection
	@CollectionTable(name="question_options", joinColumns = @JoinColumn(name = "question_id"))
	@Column(name = "options")
	private List<String> options;
	
	@ElementCollection
	@CollectionTable(name="correct_answers", joinColumns = @JoinColumn(name = "question_id"))
	@Column(name = "correct_option")
	private List<String> correctOptions;
	
	@Enumerated(EnumType.STRING)
	private QuestionType questionType;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "question", fetch = FetchType.EAGER, orphanRemoval = true)
	private List<ExamQuestionMapping> examQuestionMappings;
	
//	@JsonIgnore
//	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
//	private QuestionResponse questionResponse;
}
