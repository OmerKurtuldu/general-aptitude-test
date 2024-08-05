package com.gyt.questionservice.business.dtos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionDto {
    private Long id;
    private String text;
    private Boolean isCorrect;
    private String imageUrl;
}