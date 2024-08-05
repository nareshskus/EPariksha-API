package com.quizonline2.serviceImpl;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.quizonline2.exceptions.CategoryNotFoundException;
import com.quizonline2.model.Category;
import com.quizonline2.model.DTO.CategoryDto;
import com.quizonline2.repository.CategoryRepository;
import com.quizonline2.repository.QuestionRepository;
import com.quizonline2.service.CategoryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final QuestionRepository questionRepository;
	
	//This method creates a new category in the database. CategoryDto is created which takes categoryName as input.
	@Override
	public Category createCategory(CategoryDto categoryDto) {
		Category category = new Category();
		BeanUtils.copyProperties(categoryDto, category);
		return categoryRepository.save(category);
	}

	//This method fetches all the categories from the database.
	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	
	//This method updates the already existing category name of any category.
	@Override
	public Category updateCategory(Long categoryId, CategoryDto categoryDto) {
		try {
			Category existingCategory = getCategoryById(categoryId);
			BeanUtils.copyProperties(categoryDto, existingCategory);
			return categoryRepository.save(existingCategory);
		} catch (CategoryNotFoundException e) {
			throw new CategoryNotFoundException("Category not found with id: "+categoryId);
		}
	}

	//This method fetches the category of a specific categoryId.
	@Override
	public Category getCategoryById(Long categoryId) {
		return categoryRepository.findById(categoryId).orElseThrow(()->
			new CategoryNotFoundException("Category not found with id: "+categoryId));
	}

	//This method deleted a specific category from database using categoryId.
	@Override
	public void deleteCategory(Long categoryId) {
		if(categoryRepository.existsById(categoryId)) {
			categoryRepository.deleteById(categoryId);
		} else {
			throw new CategoryNotFoundException("Category not found with id: "+categoryId);
		}
		
	}
	
}
