package com.gyt.examservice.api.controllers;

import com.gyt.examservice.business.abstracts.RuleService;
import com.gyt.examservice.business.dtos.request.update.UpdateRuleRequest;
import com.gyt.examservice.business.dtos.response.get.GetRuleResponse;
import com.gyt.examservice.business.dtos.response.getAll.GetAllRuleResponse;
import com.gyt.examservice.business.dtos.response.update.UpdateRuleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rules")
@RequiredArgsConstructor
public class RuleController {
    private final RuleService ruleService;

    @PutMapping("/update-rule")
    public ResponseEntity<UpdateRuleResponse> updateRule(@RequestBody @Valid UpdateRuleRequest updateRuleRequest) {
        UpdateRuleResponse updateRuleResponse = ruleService.updateRule(updateRuleRequest);
        return new ResponseEntity<>(updateRuleResponse, HttpStatus.OK);
    }

    @GetMapping("/{ruleId}")
    public ResponseEntity<GetRuleResponse> getRuleById(@PathVariable Long ruleId) {
        GetRuleResponse ruleResponse = ruleService.getRuleById(ruleId);
        return new ResponseEntity<>(ruleResponse, HttpStatus.OK);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<GetAllRuleResponse> getAllRules(
            @RequestParam int pageNumber,
            @RequestParam int pageSize) {
        Page<GetAllRuleResponse> allRules = ruleService.getAllRules(pageNumber, pageSize);
        return allRules;
    }

    @DeleteMapping("/{ruleId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRuleById(@PathVariable Long ruleId) {
        ruleService.deleteRuleById(ruleId);
    }

}
