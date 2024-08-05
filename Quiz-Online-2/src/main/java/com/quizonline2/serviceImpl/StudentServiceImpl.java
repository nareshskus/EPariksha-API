package com.quizonline2.serviceImpl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.quizonline2.exceptions.DuplicateUserNameException;
import com.quizonline2.exceptions.StudentNotFoundException;
import com.quizonline2.model.Student;
import com.quizonline2.repository.StudentRepository;
import com.quizonline2.service.StudentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;
	
	@Override
	public Student registerStudent(Student student) {
		if(studentRepository.existsByUserName(student.getUserName())) {
			throw new DuplicateUserNameException("Username Already Exists: "+student.getUserName());
		}
		return studentRepository.save(student);
	}

	@Override
	public Optional<Student> login(String userName, String password) {
		Optional<Student> student = Optional.of(studentRepository.findByUserName(userName));
		if(student.isPresent() && student.get().verifyPassword(password)) {
			return student;
		}
		return Optional.empty();
	}

	@Override
	public Student getStudentById(Long studenyId) {
			return studentRepository.findById(studenyId). orElseThrow(
					()-> new StudentNotFoundException("Student not found with id: "+studenyId));
		
	}

}
