package com.gyt.questionservice.models.entities;

import com.gyt.corepackage.models.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "questions")
public class Question extends BaseEntity {

    @Column(nullable = false, length = 2000)
    private String text;

    @Column
    private Long creatorId;

    @Column(nullable = false)
    private Boolean isEditable = true;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, mappedBy = "question")
    private List<Option> options;

    @Column
    private String imageUrl;

}