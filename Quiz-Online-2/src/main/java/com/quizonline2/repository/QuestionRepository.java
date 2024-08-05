package com.quizonline2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quizonline2.model.Question;

import jakarta.transaction.Transactional;
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

	List<Question> findByCategoryCategoryName(String categoryName);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM dbo.question WHERE category_id = :categoryId", nativeQuery = true)
	void deleteQuestionByCategory(@Param("categoryId") Long categoryId);
}
