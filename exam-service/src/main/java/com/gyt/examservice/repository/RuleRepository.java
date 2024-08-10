package com.gyt.examservice.repository;


import com.gyt.examservice.model.entities.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule, Long> {
}
