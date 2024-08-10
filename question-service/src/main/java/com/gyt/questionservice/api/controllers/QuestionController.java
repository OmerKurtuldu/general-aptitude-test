package com.gyt.questionservice.api.controllers;

import com.gyt.questionservice.business.abstracts.QuestionService;
import com.gyt.questionservice.business.dtos.dto.OptionDto;
import com.gyt.questionservice.business.dtos.request.create.CreateOptionRequest;
import com.gyt.questionservice.business.dtos.request.create.CreateQuestionRequest;
import com.gyt.questionservice.business.dtos.request.update.UpdateQuestionEditableRequest;
import com.gyt.questionservice.business.dtos.request.update.UpdateQuestionRequest;
import com.gyt.questionservice.business.dtos.dto.QuestionDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/create")
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody @Valid CreateQuestionRequest createQuestionRequest) {
        log.info("Create request received for question: {} {}", createQuestionRequest.getText(), createQuestionRequest.getImageUrl());
        QuestionDto response = questionService.createQuestion(createQuestionRequest);
        log.info("Question created successfully with ID: {}", response.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<QuestionDto> updateQuestion(@RequestBody @Valid UpdateQuestionRequest updateQuestionRequest) {
        log.info("Update request received for question with ID: {}", updateQuestionRequest.getId());
        QuestionDto response = questionService.updateQuestion(updateQuestionRequest);
        log.info("Question with ID: {} updated successfully", response.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable Long id) {
        log.info("Get request received for question with ID: {}", id);
        QuestionDto response = questionService.getQuestionById(id);
        log.info("Question with ID: {} retrieved successfully", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all-questions")
    @ResponseStatus(HttpStatus.OK)
    public Page<QuestionDto> getAllQuestions(@RequestParam int page, @RequestParam int size) {
        log.info("Get all questions request received for page: {}, size: {}", page, size);
        Page<QuestionDto> allQuestion = questionService.getAllQuestions(page, size);
        log.info("Questions retrieved successfully for page: {}, size: {}", page, size);
        return allQuestion;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestionByID(@PathVariable Long id) {
        log.info("Delete request received for question with ID: {}", id);
        questionService.deleteQuestionById(id);
        log.info("Question with ID: {} deleted successfully", id);
    }

    @PostMapping("/{questionId}/addOption")
    @ResponseStatus(HttpStatus.OK)
    public OptionDto addOptionToQuestion(@PathVariable Long questionId, @RequestBody @Valid CreateOptionRequest createOptionRequest) {
        log.info("Add option request received for question with ID: {}", questionId);
        OptionDto response = questionService.addOptionToQuestion(questionId, createOptionRequest);
        log.info("Option added to question with ID: {} successfully", questionId);
        return response;
    }

    @PostMapping("/update/questions-editable-status")
    @ResponseStatus(HttpStatus.OK)
    public void UpdateQuestionsEditableStatus(@RequestBody UpdateQuestionEditableRequest request) {
        questionService.updateQuestionsEditableStatus(request);

    }
}
