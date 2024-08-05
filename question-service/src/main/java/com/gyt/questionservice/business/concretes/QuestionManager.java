package com.gyt.questionservice.business.concretes;

import com.gyt.corepackage.business.abstracts.MessageService;
import com.gyt.corepackage.utils.exceptions.types.BusinessException;
import com.gyt.questionservice.api.clients.ManagementServiceClient;
import com.gyt.questionservice.business.abstracts.OptionService;
import com.gyt.questionservice.business.abstracts.QuestionService;
import com.gyt.questionservice.business.dtos.dto.OptionDto;
import com.gyt.questionservice.business.dtos.dto.QuestionDto;
import com.gyt.questionservice.business.dtos.request.create.CreateOptionRequest;
import com.gyt.questionservice.business.dtos.request.create.CreateQuestionRequest;
import com.gyt.questionservice.business.dtos.request.update.UpdateQuestionRequest;
import com.gyt.questionservice.business.dtos.response.GetUserResponse;
import com.gyt.questionservice.business.messages.Messages;
import com.gyt.questionservice.business.rules.OptionBusinessRules;
import com.gyt.questionservice.business.rules.QuestionBusinessRules;
import com.gyt.questionservice.mapper.OptionMapper;
import com.gyt.questionservice.mapper.QuestionMapper;
import com.gyt.questionservice.models.entities.Option;
import com.gyt.questionservice.models.entities.Question;
import com.gyt.questionservice.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuestionManager implements QuestionService {
    private final QuestionRepository questionRepository;
    private final ManagementServiceClient managementServiceClient;
    private final QuestionBusinessRules questionBusinessRules;
    private final OptionBusinessRules optionBusinessRules;
    private final OptionService optionService;
    private final MessageService messageService;
    private final QuestionMapper questionMapper;
    private final OptionMapper optionMapper;

    @Override
    @Transactional
    public QuestionDto createQuestion(CreateQuestionRequest request) {
        log.info("Create request received for question with text: {}", request.getText());

        questionBusinessRules.textAndImageValidationRule(request.getText(), request.getImageUrl());
        optionBusinessRules.validateCorrectOptionExists(request.getOptionRequestList());

        Question question = createAndSaveQuestion(request);

        final Question savedQuestion = questionRepository.save(question);

        List<OptionDto> options = createAndSaveOptions(request.getOptionRequestList(), question);

        QuestionDto questionDto = questionMapper.questionToDto(question);
        questionDto.setOptions(options);

        log.info("Question with text: {} created successfully", request.getText());
        return questionDto;
    }

    @Override
    public QuestionDto updateQuestion(UpdateQuestionRequest request) {
        log.info("Update request received for question with ID: {}", request.getId());

        Question foundQuestion = questionRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException(messageService.getMessage(Messages.QuestionErrors.QuestionShouldBeExist)));

        questionBusinessRules.textAndImageValidationRule(request.getText(), request.getImageUrl());
        questionBusinessRules.userAuthorizationCheck(foundQuestion.getCreatorId());

        Question question = questionMapper.updateQuestionRequestToEntity(request);
        question.setCreatorId(foundQuestion.getCreatorId());
        questionRepository.save(question);

        log.info("Question with ID: {} updated successfully", request.getId());

        return questionMapper.questionToDto(question);
    }

    @Override
    public QuestionDto getQuestionById(Long id) {
        log.info("Get request received for question with ID: {}", id);

        Question foundQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(messageService.getMessage(Messages.QuestionErrors.QuestionShouldBeExist)));

        List<OptionDto> optionDTOS = foundQuestion.getOptions().stream()
                .map(optionMapper::optionToDto).toList();

        QuestionDto response = questionMapper.questionToDto(foundQuestion);
        response.setOptions(optionDTOS);

        log.info("Question with ID: {} retrieved successfully", id);

        return response;
    }

    @Override
    public Page<QuestionDto> getAllQuestions(int page, int size) {
        log.info("Get all questions request received for page: {}, size: {}", page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Question> questionsPage = questionRepository.findAll(pageable);
        Page<QuestionDto> responsePage = questionsPage.map(
                question -> {
                    List<OptionDto> optionDTOS = question.getOptions().stream()
                            .map(optionMapper::optionToDto).toList();
                    QuestionDto response = questionMapper.questionToDto(question);
                    response.setOptions(optionDTOS);
                    return response;
                }
        );
        log.info("Retrieved {} questions for page: {}, size: {}", responsePage.getTotalElements(), page, size);

        return responsePage;
    }

    @Override
    public void deleteQuestionById(Long id) {
        log.info("Delete request received for question with ID: {}", id);

        Question foundQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(messageService.getMessage(Messages.QuestionErrors.QuestionShouldBeExist)));

        questionBusinessRules.userAuthorizationCheck(foundQuestion.getCreatorId());

        questionRepository.deleteById(id);

        log.info("Question with ID: {} deleted successfully", id);
    }

    @Override
    public OptionDto addOptionToQuestion(Long questionId, CreateOptionRequest request) {
        log.info("Add option request received for question with ID: {}", questionId);

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new BusinessException(messageService.getMessage(Messages.QuestionErrors.QuestionShouldBeExist)));

        optionBusinessRules.validateMaxFiveOptions(question.getOptions().size());
        optionBusinessRules.validateTextAndImagePresence(request.getText(), request.getImageUrl());

        Option option = optionMapper.createRequestToOption(request);
        option.setQuestion(question);

        question.getOptions().add(option);

        optionService.saveOption(option);
        questionRepository.save(question);

        log.info("Option added to question with ID: {} successfully", questionId);

        return optionMapper.optionToDto(option);
    }


    private Question createAndSaveQuestion(CreateQuestionRequest request) {
        Question question = questionMapper.createQuestionRequestToEntity(request);
        Long creatorId = getCreatorId(managementServiceClient.getAuthenticatedUser());
        question.setCreatorId(creatorId);
        return questionRepository.save(question);
    }

    private List<OptionDto> createAndSaveOptions(List<CreateOptionRequest> optionRequests, Question question) {
        return optionRequests.stream()
                .map(request -> createAndSaveOption(request, question))
                .collect(Collectors.toList());
    }

    private OptionDto createAndSaveOption(CreateOptionRequest request, Question question) {
        optionBusinessRules.validateTextAndImagePresence(request.getText(), request.getImageUrl());

        Option option = optionMapper.createRequestToOption(request);
        option.setQuestion(question);
        optionService.saveOption(option);

        return optionMapper.optionToDto(option);
    }


    public Long getCreatorId(GetUserResponse getUserResponse) {
        boolean hasOrganizationRole = getUserResponse.getRoles().contains("ORGANIZATION");

        if (hasOrganizationRole) {
            log.info("User with ID: {} has organization role", getUserResponse.getId());
            return getUserResponse.getId();
        }
        log.warn("User with ID: {} does not have organization role", getUserResponse.getId());
        return null;
    }
}
