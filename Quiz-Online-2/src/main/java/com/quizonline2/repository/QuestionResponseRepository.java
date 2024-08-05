package com.quizonline2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.quizonline2.model.QuestionResponse;

import jakarta.transaction.Transactional;

public interface QuestionResponseRepository extends JpaRepository<QuestionResponse, Long> {

	List<QuestionResponse> findAllByExamResultExamResultId(Long examResultId);	

}
