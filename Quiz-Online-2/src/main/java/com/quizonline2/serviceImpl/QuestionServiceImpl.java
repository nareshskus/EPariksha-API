package com.quizonline2.serviceImpl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.quizonline2.exceptions.CategoryNotFoundException;
import com.quizonline2.exceptions.NullIdException;
import com.quizonline2.exceptions.QuestionNotFoundException;
import com.quizonline2.model.Category;
import com.quizonline2.model.Question;
import com.quizonline2.model.DTO.QuestionDto;
import com.quizonline2.repository.CategoryRepository;
import com.quizonline2.repository.ExamQuestionRepository;
import com.quizonline2.repository.QuestionRepository;
import com.quizonline2.service.QuestionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionServiceImpl implements QuestionService {

	private final QuestionRepository questionRepository;
	private final CategoryRepository categoryRepository;
	private final ExamQuestionRepository examQuestionRepository;
	
	/*This method takes QuestionDto as input which includes categoryName as well and create new Question in already
	existing Category. If category doesn't existing it throws CategoryNotFoundException.*/
	@Override
	public Question createQuestion(QuestionDto questionDto) {
		try {
			Question question = new Question();
			//BeanUtils is used to copy questionDto to actual question object because we are not taking questionId as input.
			BeanUtils.copyProperties(questionDto, question);
			
			Category category= categoryRepository.findByCategoryName(questionDto.getCategoryName());
			
			//This is checking that if category exists then only it will add question to specific category otherwise exception is thrown.
			if(category==null) {
				throw new CategoryNotFoundException("Category Not Found:" +questionDto.getCategoryName());
			}
			question.setCategory(category);
			return questionRepository.save(question);
		} catch (CategoryNotFoundException e) {
			throw new CategoryNotFoundException("Category not found: "+questionDto.getCategoryName());
		}
	}
	
	//This method fetches question by questionId
	@Override
	public Question getQuestionById(Long questionId) {
		if(questionId == null)
			throw new NullIdException("Null id found as questionId: ");
		return questionRepository.findById(questionId).orElseThrow(()-> 
					new QuestionNotFoundException("Question not found with id: "+questionId));
	}
	
	//This method fetches all the questions present in the question table of database.
	@Override
	public List<Question> getAllQuestions() {
		return questionRepository.findAll();
	}
	
	//This method again takes input of questionDto and update the values in the specific questionId.
	@Override
	public Question updateQuestion(Long questionId, QuestionDto questionDto) {
		Question existingQuestion = getQuestionById(questionId);
		BeanUtils.copyProperties(questionDto, existingQuestion);
		//This method is looking if a category exists by a categoryName or not.
		Category category= categoryRepository.findByCategoryName(questionDto.getCategoryName());
		
		//If the category doesn't exists then exception is thrown otherwise Question is updated to the specific category.
		if(category==null) {
			throw new CategoryNotFoundException("Category Not Found:" +questionDto.getCategoryName());
		}
		existingQuestion.setCategory(category);
		return questionRepository.save(existingQuestion);

	}
	
	//This method deletes a question by questionId.
	@Override
	public void deleteQuestion(Long questionId) {
		if(questionRepository.existsById(questionId)) {
			examQuestionRepository.deleteQuestionFromAllExams(questionId);
			questionRepository.deleteById(questionId);
		}else {
			throw new QuestionNotFoundException("Question not found with id: "+questionId);
		}
	}
	
	//This method fetches all the question from a specific categoryName.
	@Override
	public List<Question> getQuestionsByCategory(String categoryName) {
		
		//Method findByCategoryCategoryName() is created in question repository to search question of specific category.
		if(questionRepository.findByCategoryCategoryName(categoryName) != null) {
			return questionRepository.findByCategoryCategoryName(categoryName);
		} else {
			throw new CategoryNotFoundException("Category not found with name: "+categoryName);
		}
	}
	
	
	
}
