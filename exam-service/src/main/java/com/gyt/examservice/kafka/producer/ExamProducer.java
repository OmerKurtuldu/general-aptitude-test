package com.gyt.examservice.kafka.producer;

import com.gyt.corepackage.events.exam.CreatedExamEvent;
import com.gyt.corepackage.events.question.CreatedQuestionEvent;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExamProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExamProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendExamForCreate(CreatedExamEvent createdExamEvent) {
        LOGGER.info(String.format("Exam created event =>%s", createdExamEvent));

        Message<CreatedExamEvent> message = MessageBuilder
                .withPayload(createdExamEvent)
                .setHeader(KafkaHeaders.TOPIC, "exam-created")
                .build();
        kafkaTemplate.send(message);
    }
}
