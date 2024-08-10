package com.gyt.examservice.mapper;

import com.gyt.examservice.business.dtos.request.create.CreateExamRequest;
import com.gyt.examservice.business.dtos.request.update.UpdateExamRequest;
import com.gyt.examservice.business.dtos.response.create.CreateExamResponse;
import com.gyt.examservice.business.dtos.response.get.GetExamResponse;
import com.gyt.examservice.business.dtos.response.get.GetQuestionResponse;
import com.gyt.examservice.business.dtos.response.getAll.GetAllExamResponse;
import com.gyt.examservice.business.dtos.response.update.UpdateExamResponse;

import com.gyt.examservice.model.entities.Exam;
import com.gyt.questionservice.GrpcGetQuestionResponse;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ExamMapper {

    Exam createRequestToExam(CreateExamRequest request);
    CreateExamResponse createExamToResponse(Exam exam);

    Exam updateRequestToExam(UpdateExamRequest updateExamRequest);
    UpdateExamResponse updateExamToResponse(Exam exam);

    GetExamResponse getExamToResponse(Exam exam);

    GetAllExamResponse getAllExamToResponse(Exam exam);

    GetQuestionResponse grpcGetQuestionResponseToResponse(GrpcGetQuestionResponse grpcResponse);

}
