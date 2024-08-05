package com.quizonline2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quizonline2.model.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByCategoryName(String categoryName);

}
