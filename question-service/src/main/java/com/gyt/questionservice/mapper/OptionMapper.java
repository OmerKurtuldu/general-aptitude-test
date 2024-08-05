package com.gyt.questionservice.mapper;

import com.gyt.questionservice.business.dtos.dto.OptionDto;
import com.gyt.questionservice.business.dtos.request.create.CreateOptionRequest;
import com.gyt.questionservice.business.dtos.request.update.UpdateOptionRequest;
import com.gyt.questionservice.models.entities.Option;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface OptionMapper {
    OptionDto optionToDto(Option option);
    Option createRequestToOption(CreateOptionRequest request);
    Option updateRequestToOption(UpdateOptionRequest request);

    OptionDto optionToDtoWithQuestionId(Option option);
}

