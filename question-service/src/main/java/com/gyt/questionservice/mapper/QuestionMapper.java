package com.gyt.questionservice.mapper;

import com.gyt.questionservice.business.dtos.dto.QuestionDto;
import com.gyt.questionservice.business.dtos.request.create.CreateQuestionRequest;
import com.gyt.questionservice.business.dtos.request.update.UpdateQuestionRequest;
import com.gyt.questionservice.models.entities.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question createQuestionRequestToEntity(CreateQuestionRequest request);

    Question updateQuestionRequestToEntity(UpdateQuestionRequest request);

    QuestionDto questionToDto(Question question);

    Question dtoToQuestion(QuestionDto dto);
}
