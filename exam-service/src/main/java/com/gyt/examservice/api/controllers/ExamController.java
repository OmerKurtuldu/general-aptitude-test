package com.gyt.examservice.api.controllers;


import com.gyt.examservice.business.abstracts.ExamService;
import com.gyt.examservice.business.dtos.request.create.CreateExamRequest;
import com.gyt.examservice.business.dtos.request.update.UpdateExamRequest;
import com.gyt.examservice.business.dtos.response.create.CreateExamResponse;
import com.gyt.examservice.business.dtos.response.get.GetExamResponse;
import com.gyt.examservice.business.dtos.response.getAll.GetAllExamResponse;
import com.gyt.examservice.business.dtos.response.update.UpdateExamResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping("/create")
    public ResponseEntity<CreateExamResponse> createExam(@RequestBody @Valid CreateExamRequest createExamRequest) {
        CreateExamResponse exam = examService.createExam(createExamRequest);
        return new ResponseEntity<>(exam, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateExamResponse> updateExam(@RequestBody @Valid UpdateExamRequest updateExamRequest){
        UpdateExamResponse updateExamResponse = examService.updateExam(updateExamRequest);
        return new ResponseEntity<>(updateExamResponse, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetExamResponse> getExamById(@PathVariable Long id){
        GetExamResponse exam = examService.getExamById(id);
        return new ResponseEntity<>(exam,HttpStatus.OK);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<GetAllExamResponse> getAllQuestions(
            @RequestParam int pageNumber,
            @RequestParam int pageSize) {
        Page<GetAllExamResponse> allQuestion = examService.getAllExam(pageNumber, pageSize);
        return allQuestion;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExamById(@PathVariable Long id){
        examService.deleteExamById(id);
    }

    @PostMapping("/{examId}/questions/add")
    @ResponseStatus(HttpStatus.OK)
    public void addQuestionToExam(@PathVariable Long examId, @RequestParam Long questionId){
        examService.addQuestionToExam(examId, questionId);
    }

    @PostMapping("/{examId}/questions/remove")
    @ResponseStatus(HttpStatus.OK)
    public void removeQuestionFromExam(@PathVariable Long examId, @RequestParam Long questionId){
        examService.removeQuestionFromExam(examId, questionId);
    }

    @PutMapping("/{examId}/extend-end-date")
    public ResponseEntity<Void> extendEndDate(@PathVariable Long examId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime newEndDate) {
        examService.extendExamEndDate(examId, newEndDate);
        return ResponseEntity.ok().build();
    }
}
