package com.gyt.questionservice.business.concretes;

import com.gyt.corepackage.business.abstracts.MessageService;
import com.gyt.corepackage.utils.exceptions.types.BusinessException;
import com.gyt.questionservice.GrpcGetQuestionRequest;
import com.gyt.questionservice.GrpcGetQuestionResponse;
import com.gyt.questionservice.GrpcOptionDTO;
import com.gyt.questionservice.QuestionServiceGrpc;
import com.gyt.questionservice.business.messages.Messages;

import com.gyt.questionservice.models.entities.Option;
import com.gyt.questionservice.models.entities.Question;
import com.gyt.questionservice.repository.QuestionRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

// TODO: 9.08.2024 best practice nerede oluşturulur 
@RequiredArgsConstructor
@GrpcService
public class QuestionGrpcImpl extends QuestionServiceGrpc.QuestionServiceImplBase {

    private final QuestionRepository questionRepository;
    private final MessageService messageService;

    @Override
    public void getQuestionByID(GrpcGetQuestionRequest request, StreamObserver<GrpcGetQuestionResponse> responseObserver) {
        Long id = request.getId();

        Question question = questionRepository.findById(id).orElseThrow(
                () -> new BusinessException(messageService.getMessage(Messages.QuestionErrors.QuestionShouldBeExist)));

        List<Option> options = question.getOptions();

        List<GrpcOptionDTO> optionGrpcList = options.stream().map(option -> GrpcOptionDTO.newBuilder()
                .setId(option.getId())
                .setText(option.getText())
                .setImageUrl(option.getImageUrl())
                .build()).toList();


        GrpcGetQuestionResponse response = GrpcGetQuestionResponse.newBuilder()
                .setId(question.getId())
                .setText(question.getText())
                .setIsEditable(question.getIsEditable())
                .setImageUrl(question.getImageUrl())
                .addAllOptions(optionGrpcList)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}





