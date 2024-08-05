package com.gyt.questionservice.business.dtos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private Long id;
    private String text;
    private Long creatorId;
    private Boolean isEditable;
    private String imageUrl;
    private List<OptionDto> options;
}