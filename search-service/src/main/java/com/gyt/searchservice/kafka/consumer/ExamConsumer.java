package com.gyt.searchservice.kafka.consumer;

import com.gyt.corepackage.events.exam.CreatedExamEvent;
import com.gyt.corepackage.events.question.CreatedQuestionEvent;
import com.gyt.searchservice.business.abstracts.ExamService;
import com.gyt.searchservice.mapper.ExamMapper;
import com.gyt.searchservice.models.entities.Exam;
import com.gyt.searchservice.models.entities.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExamConsumer {
    private final ExamMapper examMapper;
    private final ExamService examService;

    @KafkaListener(topics = "exam-created", groupId = "search")
    public void consumeCreatedExamEvent(CreatedExamEvent event) {
        log.info("Received CreatedExamEvent with ID: {}", event.getId());

        Exam exam = examMapper.createdExamEventToExam(event);
        examService.add(exam);

        log.info("Question with ID: {} has been added.", exam.getId());
    }



}
