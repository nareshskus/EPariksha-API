package com.quizonline2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quizonline2.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
