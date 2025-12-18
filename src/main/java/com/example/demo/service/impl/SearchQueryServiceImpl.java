package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.model.SearchQueryRecord;
import com.example.demo.repository.EmployeeSkillRepository;
import com.example.demo.repository.SearchQueryRecordRepository;
import com.example.demo.service.SearchQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.StringJoiner;

@Service
public class SearchQueryServiceImpl implements SearchQueryService {

    private final SearchQueryRecordRepository searchQueryRecordRepository;
    private final EmployeeSkillRepository employeeSkillRepository;

    public SearchQueryServiceImpl(
            SearchQueryRecordRepository searchQueryRecordRepository,
            EmployeeSkillRepository employeeSkillRepository
    ) {
        this.searchQueryRecordRepository = searchQueryRecordRepository;
        this.employeeSkillRepository = employeeSkillRepository;
    }

    @Override
    public List<Employee> searchEmployeesBySkills(
            List<String> skills,
            Long userId
    ) {

        if (skills == null || skills.isEmpty()) {
            throw new IllegalArgumentException("must not be empty");
        }

        List<Employee> employees =
                employeeSkillRepository.findEmployeesByAllSkillNames(
                        skills, userId
                );

        StringJoiner joiner = new StringJoiner(",");
        skills.forEach(joiner::add);

        SearchQueryRecord record = new SearchQueryRecord();
        record.setSearcherId(userId);
        record.setSkillsRequested(joiner.toString());
        record.setResultsCount(employees.size());

        searchQueryRecordRepository.save(record);

        return employees;
    }

    @Override
    public SearchQueryRecord getQueryById(Long id) {
        return searchQueryRecordRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Search query not found")
                );
    }

    @Override
    public List<SearchQueryRecord> getQueriesForUser(Long userId) {
        return searchQueryRecordRepository.findBySearcherId(userId);
    }
}
