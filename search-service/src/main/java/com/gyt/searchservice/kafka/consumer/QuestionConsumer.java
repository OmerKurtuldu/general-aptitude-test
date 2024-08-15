package com.gyt.searchservice.kafka.consumer;

import com.gyt.corepackage.events.question.CreatedQuestionEvent;
import com.gyt.corepackage.events.question.DeletedQuestionEvent;
import com.gyt.corepackage.events.question.UpdatedQuestionEvent;
import com.gyt.searchservice.business.abstracts.QuestionSearchService;
import com.gyt.searchservice.mapper.QuestionMapper;
import com.gyt.searchservice.models.entities.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionConsumer {

    private final QuestionMapper questionMapper;
    private final QuestionSearchService questionSearchService;

    @KafkaListener(topics = "question-created", groupId = "search")
    public void consumeCreatedQuestionEvent(CreatedQuestionEvent event) {
        log.info("Received CreatedQuestionEvent with ID: {}", event.getId());
        Question question = questionMapper.createdQuestionEventToQuestion(event);
        questionSearchService.add(question);
        log.info("Question with ID: {} has been added.", question.getId());
    }

    @KafkaListener(topics = "question-updated", groupId = "search")
    public void consumeUpdatedQuestionEvent(UpdatedQuestionEvent event) {
        log.info("Received UpdatedExamEvent with ID: {}", event.getId());
        Question question = questionMapper.updatedQuestionEventToQuestion(event);
        questionSearchService.update(question);
        log.info("Question with ID: {} has been updated.", question.getId());
    }

    @KafkaListener(topics = "question-deleted", groupId = "search")
    public void consumeDeletedQuestionEvent(DeletedQuestionEvent event) {
        log.info("Received DeletedQuestionEvent with ID: {}", event.getId());
        questionSearchService.delete(event.getId());
        log.info("Question with ID: {} has been deleted.", event.getId());
    }
}
