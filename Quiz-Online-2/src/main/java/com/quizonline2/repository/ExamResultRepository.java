package com.quizonline2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quizonline2.model.Exam;
import com.quizonline2.model.ExamResult;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

	boolean existsByExamExamId(Long examId);

	boolean existsByExamExamIdAndStudentStudentIdAndCompletedIsTrue(Long examId, Long studentId);

	ExamResult findByExamExamIdAndStudentStudentId(Long examId, Long studentId);

	boolean existsByExamExamIdAndStudentStudentId(Long examId, Long studentId);

	List<ExamResult> findAllByStudentStudentIdAndCompletedIsTrue(Long studentId);

	List<ExamResult> findByExamExamId(Long examId);

}
