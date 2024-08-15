package com.gyt.examservice.api.controllers;

import com.gyt.examservice.business.abstracts.RuleService;
import com.gyt.examservice.business.dtos.request.update.UpdateRuleRequest;
import com.gyt.examservice.business.dtos.response.get.GetRuleResponse;
import com.gyt.examservice.business.dtos.response.getAll.GetAllRuleResponse;
import com.gyt.examservice.business.dtos.response.update.UpdateRuleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/rules")
@RequiredArgsConstructor
public class RuleController {
    private final RuleService ruleService;

    @PutMapping("/update-rule")
    public ResponseEntity<UpdateRuleResponse> updateRule(@RequestBody @Valid UpdateRuleRequest updateRuleRequest) {
        log.info("Received request to update rule: {}", updateRuleRequest);
        UpdateRuleResponse updateRuleResponse = ruleService.updateRule(updateRuleRequest);
        log.info("Rule updated successfully with ID: {}", updateRuleResponse.getId());
        return new ResponseEntity<>(updateRuleResponse, HttpStatus.OK);
    }

    @GetMapping("/{ruleId}")
    public ResponseEntity<GetRuleResponse> getRuleById(@PathVariable Long ruleId) {
        log.info("Received request to get rule by ID: {}", ruleId);
        GetRuleResponse ruleResponse = ruleService.getRuleById(ruleId);
        log.info("Retrieved rule with ID: {}", ruleId);
        return new ResponseEntity<>(ruleResponse, HttpStatus.OK);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<GetAllRuleResponse> getAllRules(
            @RequestParam int pageNumber,
            @RequestParam int pageSize) {
        log.info("Received request to get all rules with page: {} and size: {}", pageNumber, pageSize);
        Page<GetAllRuleResponse> allRules = ruleService.getAllRules(pageNumber, pageSize);
        log.info("Retrieved {} rules", allRules.getTotalElements());
        return allRules;
    }

    @DeleteMapping("/{ruleId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRuleById(@PathVariable Long ruleId) {
        log.info("Received request to delete rule by ID: {}", ruleId);
        ruleService.deleteRuleById(ruleId);
        log.info("Deleted rule with ID: {}", ruleId);

    }

}
