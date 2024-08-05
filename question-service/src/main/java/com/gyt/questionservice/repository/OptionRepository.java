package com.gyt.questionservice.repository;

import com.gyt.questionservice.models.entities.Option;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findAllByQuestionId(Long questionId);
}