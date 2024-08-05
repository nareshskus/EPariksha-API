package com.quizonline2.service;

import java.util.Optional;

import com.quizonline2.model.Student;

public interface StudentService {
	
	Optional<Student> login(String userName, String password);

	Student registerStudent(Student student);
	
	Student getStudentById(Long studenyId);
	
}
