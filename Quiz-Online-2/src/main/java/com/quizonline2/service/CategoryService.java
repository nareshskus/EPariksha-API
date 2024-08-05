package com.quizonline2.service;

import java.util.List;

import com.quizonline2.model.Category;
import com.quizonline2.model.DTO.CategoryDto;

public interface CategoryService {

	Category createCategory(CategoryDto categoryDto);
	public List<Category> getAllCategories();
	Category getCategoryById(Long id);
	Category updateCategory(Long id, CategoryDto categoryDto);
	void deleteCategory(Long id);
}
