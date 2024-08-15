package com.gyt.corepackage.events.question;

import com.gyt.corepackage.events.Event;
import lombok.Data;

@Data
public class UpdatedQuestionEvent implements Event {
    private Long id;
    private String text;

}
