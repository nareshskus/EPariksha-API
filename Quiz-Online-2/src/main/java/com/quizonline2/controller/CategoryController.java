package com.quizonline2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quizonline2.exceptions.CategoryNotFoundException;
import com.quizonline2.model.Category;
import com.quizonline2.model.DTO.CategoryDto;
import com.quizonline2.service.CategoryService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;
	
	@PostMapping("/addCategory")
	public ResponseEntity<Category> addCategory(@RequestBody CategoryDto categoryDto){
		Category createdcCategory = categoryService.createCategory(categoryDto);
		return new ResponseEntity<> (createdcCategory, HttpStatus.CREATED);
	}
	
	@GetMapping("/allCategory")
	public ResponseEntity<List<Category>> allCategory(){
		List<Category> categories = categoryService.getAllCategories();
		return new ResponseEntity<>(categories,HttpStatus.OK);
	}
	
	@PutMapping("/updateCategory/{categoryId}")
	public ResponseEntity<Category> updateCategory(@PathVariable long categoryId, @RequestBody CategoryDto categoryDto){
		try {
			Category updatedCategory = categoryService.updateCategory(categoryId, categoryDto);
			return new ResponseEntity<> (updatedCategory,HttpStatus.OK);
		} catch (CategoryNotFoundException e) {
			throw new CategoryNotFoundException("Category not found with id: "+categoryId);
		}
	}
	
	@DeleteMapping("/deleteCategory/{categoryId}")
	public ResponseEntity<Void> deleteCategory (@PathVariable long categoryId){
		try {
			categoryService.deleteCategory(categoryId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (CategoryNotFoundException e) {
			throw new CategoryNotFoundException("Category not found with id: "+categoryId);
		}
	}
}
