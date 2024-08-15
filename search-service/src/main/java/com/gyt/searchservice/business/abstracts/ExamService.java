package com.gyt.searchservice.business.abstracts;

import com.gyt.searchservice.core.services.models.DynamicQuery;
import com.gyt.searchservice.models.entities.Exam;
import com.gyt.searchservice.models.entities.Question;

import java.util.List;

public interface ExamService {

    void add(Exam exam);

    void update(Exam exam);

    void delete(Long id);

    List<Exam> searchQuestion(DynamicQuery dynamicQuery);
}


