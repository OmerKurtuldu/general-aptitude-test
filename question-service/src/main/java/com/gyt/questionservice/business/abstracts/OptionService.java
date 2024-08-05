package com.gyt.questionservice.business.abstracts;

import com.gyt.questionservice.business.dtos.dto.OptionDto;
import com.gyt.questionservice.business.dtos.request.update.UpdateOptionRequest;
import com.gyt.questionservice.models.entities.Option;
import org.springframework.data.domain.Page;

public interface OptionService {
    OptionDto updateOption(UpdateOptionRequest request);

    OptionDto getOptionById(Long optionId);

    Page<OptionDto> getAllOptions(int page, int size);

    void deleteOptionById(Long optionId);

    void saveOption(Option option);
}
