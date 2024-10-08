package com.gyt.examservice.api.clients;

import com.gyt.examservice.business.dtos.request.update.UpdateQuestionEditableRequest;
import com.gyt.examservice.business.dtos.response.get.GetQuestionResponse;
import com.gyt.examservice.configurations.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "question-service",url = "http://localhost:9002",configuration = FeignConfig.class)
public interface QuestionServiceClient {

    @GetMapping("/api/v1/question/getByID/{id}")
    GetQuestionResponse getQuestionById(@PathVariable("id") long id);

    @PostMapping("/api/v1/question/update/questions-editable-status")
    void updateQuestionsEditableStatus(@RequestBody UpdateQuestionEditableRequest updateQuestionEditableRequest);

}
