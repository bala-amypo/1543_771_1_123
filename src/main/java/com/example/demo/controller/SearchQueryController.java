package com.example.demo.controller;

import com.example.demo.dto.EmployeeSearchRequest;
import com.example.demo.model.Employee;
import com.example.demo.model.SearchQueryRecord;
import com.example.demo.service.SearchQueryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchQueryController {

    private final SearchQueryService searchQueryService;

    public SearchQueryController(SearchQueryService searchQueryService) {
        this.searchQueryService = searchQueryService;
    }

    @PostMapping("/employees")
    public List<Employee> searchEmployees(
            @RequestBody EmployeeSearchRequest request
    ) {
        return searchQueryService.searchEmployeesBySkills(
                request.getSkills(),
                request.getUserId()
        );
    }

    @GetMapping("/{id}")
    public SearchQueryRecord getQuery(@PathVariable Long id) {
        return searchQueryService.getQueryById(id);
    }

    @GetMapping("/user/{userId}")
    public List<SearchQueryRecord> getQueriesForUser(
            @PathVariable Long userId
    ) {
        return searchQueryService.getQueriesForUser(userId);
    }
}
