package com.gyt.searchservice.business.concretes;

import com.gyt.searchservice.business.abstracts.ExamService;
import com.gyt.searchservice.core.services.SearchService;
import com.gyt.searchservice.core.services.models.DynamicQuery;
import com.gyt.searchservice.models.entities.Exam;
import com.gyt.searchservice.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamManager implements ExamService {
    private final ExamRepository examRepository;
    private final SearchService searchService;

    @Override
    public void add(Exam exam) {
        examRepository.save(exam);
    }

    @Override
    public void update(Exam exam) {
        examRepository.save(exam);
    }

    @Override
    public void delete(Long id) {
        examRepository.deleteById(id);
    }

    @Override
    public List<Exam> searchQuestion(DynamicQuery dynamicQuery) {
        return searchService.dynamicSearch(dynamicQuery, Exam.class);
    }
}