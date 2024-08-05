package com.gyt.questionservice.repository;

import com.gyt.questionservice.models.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
