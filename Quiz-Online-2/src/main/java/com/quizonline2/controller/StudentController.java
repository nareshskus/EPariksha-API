package com.quizonline2.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quizonline2.exceptions.DuplicateUserNameException;
import com.quizonline2.model.Student;
import com.quizonline2.service.StudentService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

	private final StudentService studentService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerStudent(@RequestBody Student student){
		try {
			Student registeredStudent = studentService.registerStudent(student);
			return new ResponseEntity<>(registeredStudent, HttpStatus.CREATED);
		} catch (DuplicateUserNameException e) {
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", "Username already exists.");
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<Student> login(@RequestParam String userName, @RequestParam String password){
		Optional<Student> student = studentService.login(userName, password);
		return student.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
	}
	
	@GetMapping("/getStudent/{studentId}")
	public ResponseEntity<Student> getStudent(@PathVariable Long studentId){
		Student student = studentService.getStudentById(studentId);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}
	
}
