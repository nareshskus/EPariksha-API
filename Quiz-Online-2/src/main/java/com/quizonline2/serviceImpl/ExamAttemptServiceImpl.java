package com.quizonline2.serviceImpl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.quizonline2.enums.QuestionType;
import com.quizonline2.exceptions.ExamAlreadyAttemptedException;
import com.quizonline2.exceptions.ExamNotFoundException;
import com.quizonline2.exceptions.ExamResultNotFoundException;
import com.quizonline2.exceptions.QuestionNotFoundException;
import com.quizonline2.model.Exam;
import com.quizonline2.model.ExamQuestionMapping;
import com.quizonline2.model.ExamResult;
import com.quizonline2.model.Question;
import com.quizonline2.model.QuestionResponse;
import com.quizonline2.model.Student;
import com.quizonline2.model.DTO.QuestionForExamDto;
import com.quizonline2.repository.ExamRepository;
import com.quizonline2.repository.ExamResultRepository;
import com.quizonline2.repository.QuestionRepository;
import com.quizonline2.repository.QuestionResponseRepository;
import com.quizonline2.repository.StudentRepository;
import com.quizonline2.service.ExamAttemptService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ExamAttemptServiceImpl implements ExamAttemptService {
	
	private final ExamRepository examRepository;
	private final QuestionRepository questionRepository;
	private final StudentRepository studentRepository;
	private final ExamResultRepository examResultRepository;
	private final QuestionResponseRepository questionResponseRepository;

	@Override
	public List<Exam> getAllExams(Long studentId) {
		List<Exam> exams = examRepository.findAllByIsActive(true);
		Iterator<Exam> iterator = exams.iterator();
		while(iterator.hasNext()) {
			Exam exam = iterator.next();
			if(examResultRepository.existsByExamExamIdAndStudentStudentIdAndCompletedIsTrue(exam.getExamId(),studentId)) {
				iterator.remove();
			}
		}
		return exams;
	}

	@Override
	public Exam getExamById(Long examId) {
		return examRepository.findById(examId).orElseThrow(()-> 
				 new ExamNotFoundException("Exam not found with id: "+examId));
	}

	@Override
	public List<QuestionForExamDto> getQuestionsForExam(Long examId) {
		Exam exam = examRepository.findById(examId)
				.orElseThrow(()-> new ExamNotFoundException("Exam not found with id: "+examId));
		List<ExamQuestionMapping> questionMappings = exam.getExamQuestionMappings();
		if(questionMappings != null && !questionMappings.isEmpty()) {
			List<Question> questions = questionMappings.stream()
					.map(ExamQuestionMapping::getQuestion)
					.collect(Collectors.toList());
			
			return questions.stream()
					.map(this::convertToQuestionForExamDto)
					.collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}
	
	}
	
	
	private QuestionForExamDto convertToQuestionForExamDto(Question question) {
		QuestionForExamDto questionForExamDto = new QuestionForExamDto();
		questionForExamDto.setQuestionId(question.getQuestionId());
		questionForExamDto.setDescription(question.getDescription());
		questionForExamDto.setOptions(question.getOptions());
		questionForExamDto.setQuestion_type(question.getQuestionType().name());
		questionForExamDto.setCategoryName(question.getCategory().getCategoryName());
		
		return questionForExamDto;
	}

	@Override
	public ExamResult startExam(Long examId, String userName) {
		Exam exam = getExamById(examId);
		Student student = studentRepository.findByUserName(userName);
		
		if(examResultRepository.existsByExamExamIdAndStudentStudentId(examId, student.getStudentId())) {
			return examResultRepository.findByExamExamIdAndStudentStudentId(examId, student.getStudentId());
		}
		
		if(exam!= null && student!= null) {
			ExamResult examResult = new ExamResult();
			examResult.setExam(exam);
			examResult.setStudent(student);
			examResult.setCompleted(false);
			examResultRepository.save(examResult);
			return examResult;
		}
		return null;
	}

	@Override
	public void saveQuestionResponse(Long examResultId, Long questionId, List<String> selectedOptions,
			String descriptiveAnswer) {
		ExamResult examResult = examResultRepository.findById(examResultId).orElseThrow(()->
				new ExamResultNotFoundException("Result not found with id: "+examResultId));
		Question question = getQuestionById(questionId);
		
		if(examResult!= null && question != null) {
			QuestionResponse questionResponse = new QuestionResponse();
			questionResponse.setExamResult(examResult);
			questionResponse.setQuestion(question);
			questionResponse.setSelectedOptions(selectedOptions);
			questionResponse.setDescriptiveAnswer(descriptiveAnswer);
			questionResponseRepository.save(questionResponse);
		}
		
	}

	private Question getQuestionById(Long questionId) {
		return questionRepository.findById(questionId).orElseThrow(()-> 
				new QuestionNotFoundException("Question not found with id: "+questionId));
	}

	@Override
	public void submitExam(Long examResultId) {
		ExamResult examResult = examResultRepository.findById(examResultId).orElseThrow(()->
				new ExamResultNotFoundException("Result not found with id: "+examResultId));
		try {
			Exam exam = examResult.getExam();
			if(examResult != null) {
				List<QuestionResponse> questionResponses = examResult.getQuestionResponses();
				int totalQuestions = exam.getNumberQuestions();
				double totalMarks = 0;
				double obtainedMarks = 0;
				
				for(QuestionResponse questionResponse : questionResponses) {
					Question question = questionResponse.getQuestion();
					List<String> selectedOptions = questionResponse.getSelectedOptions();
					double perQuestionMarks = exam.getMaxMarks()/ totalQuestions;
					
					if(question.getQuestionType()== QuestionType.SINGLE_ANSWER) {
						System.out.println(isResponseCorrect(selectedOptions, question.getCorrectOptions()));
						if(isResponseCorrect(selectedOptions, question.getCorrectOptions())) {
							obtainedMarks+= perQuestionMarks;
						}
					} else if(question.getQuestionType()== QuestionType.MULTIPLE_ANSWER) {
						double correctOptionsCount = question.getCorrectOptions().size();
						double selectedCorrectCount = countSelectedCorrectOptions(selectedOptions, question.getCorrectOptions());
						
						obtainedMarks+= (selectedCorrectCount/ correctOptionsCount) * perQuestionMarks;
					} else if(question.getQuestionType() == QuestionType.DESCRIPTIVE) {
						obtainedMarks+= perQuestionMarks;
					}
					 totalMarks+= perQuestionMarks;
				}
//				totalMarks = exam.getMaxMarks();
				double percentageScore = (obtainedMarks/ totalMarks)*100;
				
				examResult.setObtainedMarks(obtainedMarks);
				examResult.setScore(percentageScore);
				examResult.setCompleted(true);
				
				examResultRepository.save(examResult);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private double countSelectedCorrectOptions(List<String> selectedOptions, List<String> correctOptions) {
		if(selectedOptions == null || correctOptions == null) {
			return 0;
		}
		return selectedOptions.stream().filter(correctOptions::contains).count();
	}

	private boolean isResponseCorrect(List<String> selectedOptions, List<String> correctOptions) {
		return selectedOptions != null && correctOptions != null && selectedOptions.containsAll(correctOptions)
				&& correctOptions.containsAll(selectedOptions);
	}
	
}
