package com.quizonline2.exceptions;

public class ExamAlreadyAttemptedException extends RuntimeException {

	public ExamAlreadyAttemptedException(String message) {
		super(message);
	}
}
