package com.gyt.questionservice.business.concretes;

import com.gyt.questionservice.business.abstracts.OptionService;
import com.gyt.questionservice.business.dtos.dto.OptionDto;
import com.gyt.questionservice.business.dtos.request.update.UpdateOptionRequest;
import com.gyt.questionservice.business.rules.OptionBusinessRules;
import com.gyt.questionservice.mapper.OptionMapper;
import com.gyt.questionservice.models.entities.Option;
import com.gyt.questionservice.repository.OptionRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OptionManager implements OptionService {
    private final OptionRepository optionRepository;
    private final OptionBusinessRules optionBusinessRules;
    private final OptionMapper optionMapper;

    @Override
    public OptionDto updateOption(UpdateOptionRequest request) {
        log.info("Update request received for option with ID: {}", request.getId());

        optionBusinessRules.optionShouldBeExist(request.getId());

        Option existingOption = optionRepository.findById(request.getId()).orElseThrow();

        optionBusinessRules.validateUserAuthorization(existingOption.getQuestion().getCreatorId());
        optionBusinessRules.validateTextAndImagePresence(request.getText(), request.getImageUrl());
        optionBusinessRules.validateCorrectOption(request);

        Option option = optionMapper.updateRequestToOption(request);
        option.setQuestion(existingOption.getQuestion());
        optionRepository.save(option);

        OptionDto optionDto = optionMapper.optionToDto(option);
        log.info("Option with ID: {} updated successfully", request.getId());

        return optionDto;
    }

    @Override
    public OptionDto getOptionById(Long optionId) {
        log.info("Get request received for option with ID: {}", optionId);

        optionBusinessRules.optionShouldBeExist(optionId);

        Option option = optionRepository.findById(optionId).orElseThrow();

        log.info("Successfully retrieved option with ID: {}", optionId);

        return optionMapper.optionToDtoWithQuestionId(option);
    }

    @Override
    public Page<OptionDto> getAllOptions(int page, int size) {
        log.info("Get all options request received for page: {}, size: {}", page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Option> optionsPage = optionRepository.findAll(pageable);
        log.info("Options retrieved successfully for page: {}, size: {}", page, size);

        return optionsPage.map(optionMapper::optionToDto);
    }

    @Override
    public void deleteOptionById(Long optionId) {
        log.info("Delete request received for option with ID: {}", optionId);

        Option option = optionRepository.findById(optionId).orElseThrow(() -> new NotFoundException("Option not found"));

        optionBusinessRules.optionShouldBeExist(optionId);

        List<Option> options = option.getQuestion().getOptions();

        optionBusinessRules.validateUserAuthorization(option.getQuestion().getCreatorId());
        optionBusinessRules.validateAtLeastTwoOptions(options);
        optionBusinessRules.validateAtLeastOneCorrectOptionRemaining(option.getQuestion().getId(), optionId);

        optionRepository.deleteById(optionId);

        log.info("Option with ID: {} deleted successfully", optionId);
    }

    @Override
    public void saveOption(Option option) {
        log.info("Save request received for option: {}", option);

        optionRepository.save(option);

        log.info("Option saved successfully");
    }

}
