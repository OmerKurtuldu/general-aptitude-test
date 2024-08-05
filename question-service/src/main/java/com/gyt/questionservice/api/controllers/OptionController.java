package com.gyt.questionservice.api.controllers;

import com.gyt.questionservice.business.abstracts.OptionService;
import com.gyt.questionservice.business.dtos.request.update.UpdateOptionRequest;
import com.gyt.questionservice.business.dtos.dto.OptionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/option")
@RequiredArgsConstructor
public class OptionController {

    private final OptionService optionService;

    @PutMapping("/update")
    public ResponseEntity<OptionDto> updateOption(@RequestBody UpdateOptionRequest request) {
        log.info("Update request received for option with ID: {}", request.getId());
        OptionDto response = optionService.updateOption(request);
        log.info("Option with ID: {} updated successfully", response.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OptionDto> getOption(@PathVariable Long id) {
        log.info("Get request received for option with ID: {}", id);
        OptionDto response = optionService.getOptionById(id);
        log.info("Option with ID: {} retrieved successfully", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all-options")
    @ResponseStatus(HttpStatus.OK)
    public Page<OptionDto> getAllOptions(@RequestParam int page, @RequestParam int size) {
        log.info("Get all options request received for page: {}, size: {}", page, size);
        Page<OptionDto> allOptions = optionService.getAllOptions(page, size);
        log.info("Options retrieved successfully for page: {}, size: {}", page, size);
        return allOptions;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOption(@PathVariable Long id) {
        log.info("Delete request received for option with ID: {}", id);
        optionService.deleteOptionById(id);
        log.info("Option with ID: {} deleted successfully", id);
    }
}
