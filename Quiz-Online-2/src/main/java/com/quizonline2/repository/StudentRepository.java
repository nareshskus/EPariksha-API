package com.quizonline2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quizonline2.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	Optional<Student> findByUserNameAndPassword(String userName, String password);

	Student findByUserName(String userName);

	boolean existsByUserName(String userName);

}
