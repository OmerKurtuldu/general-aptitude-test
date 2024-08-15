package com.gyt.searchservice.repository;

import com.gyt.searchservice.models.entities.Exam;
import com.gyt.searchservice.models.entities.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExamRepository extends MongoRepository<Exam, Long> {
}
