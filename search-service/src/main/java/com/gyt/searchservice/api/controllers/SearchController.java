package com.gyt.searchservice.api.controllers;

import com.gyt.searchservice.business.abstracts.QuestionSearchService;
import com.gyt.searchservice.core.services.models.DynamicQuery;
import com.gyt.searchservice.models.entities.Question;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("search-service/api/v1/questions")
@Slf4j
public class SearchController {
    private final QuestionSearchService questionSearchService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Question> searchQuestion(@RequestBody DynamicQuery dynamicQuery){
        log.info("Received search request with DynamicQuery: {}", dynamicQuery.toString());
        List<Question> results = questionSearchService.searchQuestion(dynamicQuery);
        log.info("Search completed. Found {} results.", results.size());
        return results;
    }
}
