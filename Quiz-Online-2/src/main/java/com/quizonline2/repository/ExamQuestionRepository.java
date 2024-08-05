package com.quizonline2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quizonline2.model.ExamQuestionMapping;

import jakarta.transaction.Transactional;
@Repository
public interface ExamQuestionRepository extends JpaRepository<ExamQuestionMapping, Long>{

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM dbo.exam_question_mapping WHERE exam_Id = :examId AND question_id = :questionId", nativeQuery = true)
	void deleteQuestionInExam(@Param("examId")Long examId, @Param("questionId")Long questionId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM dbo.exam_question_mapping WHERE question_id = :questionId", nativeQuery = true)
	void deleteQuestionFromAllExams(@Param("questionId")Long questionId);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM dbo.exam_question_mapping WHERE exam_id = :examId", nativeQuery = true)
	void deleteExam(@Param("examId")Long examId);

}
