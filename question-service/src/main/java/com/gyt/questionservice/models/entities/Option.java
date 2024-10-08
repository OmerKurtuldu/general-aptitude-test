package com.gyt.questionservice.models.entities;

import com.gyt.corepackage.models.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "options")
public class Option extends BaseEntity {

    @Column(nullable = false, length = 500)
    private String text;

    @Column(nullable = false)
    private Boolean isCorrect;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column
    private String imageUrl;

}