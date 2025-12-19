package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeSkill;
import com.example.demo.model.SearchQueryRecord;
import com.example.demo.repository.EmployeeSkillRepository;
import com.example.demo.repository.SearchQueryRecordRepository;
import com.example.demo.service.SearchQueryService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchQueryServiceImpl implements SearchQueryService {

    private final SearchQueryRecordRepository searchQueryRecordRepository;
    private final EmployeeSkillRepository employeeSkillRepository;

    public SearchQueryServiceImpl(SearchQueryRecordRepository searchQueryRecordRepository,
                                  EmployeeSkillRepository employeeSkillRepository) {
        this.searchQueryRecordRepository = searchQueryRecordRepository;
        this.employeeSkillRepository = employeeSkillRepository;
    }

    @Override
    public List<Employee> searchEmployeesBySkills(List<String> skills, Long userId) {

        if (skills == null || skills.isEmpty()) {
            throw new IllegalArgumentException("must not be empty");
        }

        // Fetch all employee-skill mappings
        List<EmployeeSkill> allMappings = employeeSkillRepository.findAll();

        // Map employee -> set of skill names
        Map<Employee, Set<String>> employeeSkillMap = new HashMap<>();

        for (EmployeeSkill es : allMappings) {
            if (!es.isActive()) continue;
            if (!es.getEmployee().isActive()) continue;
            if (!es.getSkill().getActive()) continue;

            employeeSkillMap
                    .computeIfAbsent(es.getEmployee(), k -> new HashSet<>())
                    .add(es.getSkill().getName());
        }

        // Filter employees who have ALL requested skills
        List<Employee> result = employeeSkillMap.entrySet().stream()
                .filter(e -> e.getValue().containsAll(skills))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Save search record
        SearchQueryRecord record = new SearchQueryRecord();
        record.setSearcherId(userId);
        record.setSkillsRequested(String.join(",", skills));
        record.setResultsCount(result.size());

        searchQueryRecordRepository.save(record);

        return result;
    }

    @Override
    public SearchQueryRecord getQueryById(Long id) {
        return searchQueryRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Search query not found"));
    }

    @Override
    public List<SearchQueryRecord> getQueriesForUser(Long userId) {
        return searchQueryRecordRepository.findBySearcherId(userId);
    }
}
