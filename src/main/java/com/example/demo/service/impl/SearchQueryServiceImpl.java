package com.example.demo.service.impl;

import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeSkill;
import com.example.demo.model.SearchQueryRecord;
import com.example.demo.repository.EmployeeSkillRepository;
import com.example.demo.repository.SearchQueryRecordRepository;
import com.example.demo.service.SearchQueryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SearchQueryServiceImpl implements SearchQueryService {

    private final SearchQueryRecordRepository searchQueryRecordRepository;
    private final EmployeeSkillRepository employeeSkillRepository;

    public SearchQueryServiceImpl(
            SearchQueryRecordRepository searchQueryRecordRepository,
            EmployeeSkillRepository employeeSkillRepository) {
        this.searchQueryRecordRepository = searchQueryRecordRepository;
        this.employeeSkillRepository = employeeSkillRepository;
    }

    @Override
    public SearchQueryRecord saveQuery(SearchQueryRecord query) {
        return searchQueryRecordRepository.save(query);
    }

    @Override
    public List<Employee> searchEmployeesBySkills(List<String> skills, Long userId) {

        if (skills == null || skills.isEmpty()) {
            throw new IllegalArgumentException("must not be empty");
        }

        // Fetch all active employee-skill mappings
        List<EmployeeSkill> allMappings =
                employeeSkillRepository.findAll();

        // Group skills by employee
        Map<Employee, Set<String>> employeeSkillsMap = new HashMap<>();

        for (EmployeeSkill mapping : allMappings) {
            if (!Boolean.TRUE.equals(mapping.getActive())) {
                continue;
            }

            employeeSkillsMap
                    .computeIfAbsent(
                            mapping.getEmployee(),
                            e -> new HashSet<>()
                    )
                    .add(mapping.getSkill().getName());
        }

        // Filter employees who have ALL requested skills
        List<Employee> result = employeeSkillsMap.entrySet().stream()
                .filter(entry ->
                        entry.getValue().containsAll(skills)
                )
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Save search query record
        SearchQueryRecord record = new SearchQueryRecord();
        record.setSearcherId(userId);
        record.setSkillsRequested(String.join(", ", skills));
        record.setResultsCount(result.size());

        searchQueryRecordRepository.save(record);

        return result;
    }

    @Override
    public SearchQueryRecord getQueryById(Long id) {
        return searchQueryRecordRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("SearchQueryRecord not found"));
    }

    @Override
    public List<SearchQueryRecord> getQueriesForUser(Long userId) {
        return searchQueryRecordRepository.findBySearcherId(userId);
    }
}
