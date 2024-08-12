package com.gyt.corepackage.utils.exceptions.problemDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProblemDetails {

    private String title;
    private String detail;
    private String status;
    private String type;
    private LocalDateTime timestamp;
    private String trackingId;
}
