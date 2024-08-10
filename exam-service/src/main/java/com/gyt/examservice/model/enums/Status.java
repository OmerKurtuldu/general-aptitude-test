package com.gyt.examservice.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    NOT_STARTED(1), // Başlamadı
    IN_PROGRESS(2), // Başladı
    FINISHED(3); // Bitti
    private final int value;

}
