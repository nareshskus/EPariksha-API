package com.quizonline2.model.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class ExamDto {

	private String examName;
	private LocalDate examDate;
	private boolean isActive;
	private int maxMarks;
	private int numberQuestions;
	private int durationMinutes;
	private LocalTime startTime;
	
	public ExamDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExamDto(String examName, LocalDate examDate, boolean isActive, int maxMarks, int numberQuestions,
			int durationMinutes, LocalTime startTime) {
		super();
		this.examName = examName;
		this.examDate = examDate;
		this.isActive = isActive;
		this.maxMarks = maxMarks;
		this.numberQuestions = numberQuestions;
		this.durationMinutes = durationMinutes;
		this.startTime = startTime;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public LocalDate getExamDate() {
		return examDate;
	}

	public void setExamDate(LocalDate examDate) {
		this.examDate = examDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(int maxMarks) {
		this.maxMarks = maxMarks;
	}

	public int getNumberQuestions() {
		return numberQuestions;
	}

	public void setNumberQuestions(int numberQuestions) {
		this.numberQuestions = numberQuestions;
	}

	public int getDurationMinutes() {
		return durationMinutes;
	}

	public void setDurationMinutes(int durationMinutes) {
		this.durationMinutes = durationMinutes;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	
	
}
