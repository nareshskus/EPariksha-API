package com.quizonline2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quizonline2.model.Exam;
@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

	List<Exam> findAllByIsActive(boolean b);

}
