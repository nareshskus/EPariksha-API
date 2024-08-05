package com.quizonline2.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class QuestionResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long questionResponseId;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "exam_result_id")
	private ExamResult examResult;
	
	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;
	
	@ElementCollection
	@CollectionTable(name = "selected_options", joinColumns = @JoinColumn(name = "question_response_id"))
	@Column(name = "selected_option")
	private List<String> selectedOptions;
	
	@Lob
	private String descriptiveAnswer;
}
