package com.gyt.questionservice.business.abstracts;

import com.gyt.questionservice.business.dtos.dto.OptionDto;
import com.gyt.questionservice.business.dtos.dto.QuestionDto;
import com.gyt.questionservice.business.dtos.request.create.CreateOptionRequest;
import com.gyt.questionservice.business.dtos.request.create.CreateQuestionRequest;
import com.gyt.questionservice.business.dtos.request.update.UpdateQuestionEditableRequest;
import com.gyt.questionservice.business.dtos.request.update.UpdateQuestionRequest;
import org.springframework.data.domain.Page;

public interface QuestionService {
    QuestionDto createQuestion(CreateQuestionRequest request);

    QuestionDto updateQuestion(UpdateQuestionRequest request);

    QuestionDto getQuestionById(Long id);

    Page<QuestionDto> getAllQuestions(int page, int size);

    void deleteQuestionById(Long id);

    OptionDto addOptionToQuestion(Long questionId, CreateOptionRequest request);

    void updateQuestionsEditableStatus(UpdateQuestionEditableRequest updateQuestionEditableRequest);
}
