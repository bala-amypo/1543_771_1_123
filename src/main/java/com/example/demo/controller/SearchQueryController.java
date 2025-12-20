package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.model.SearchQueryRecord;
import com.example.demo.service.SearchQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
public class SearchQueryController {

    private final SearchQueryService searchQueryService;

    public SearchQueryController(SearchQueryService searchQueryService) {
        this.searchQueryService = searchQueryService;
    }

    // POST /api/search/employees
    // Search employees by skills
    @PostMapping("/employees")
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestBody Map<String, Object> payload) {

        @SuppressWarnings("unchecked")
        List<String> skills = (List<String>) payload.get("skills");
        Long userId = Long.parseLong(payload.get("userId").toString());

        List<Employee> result =
                searchQueryService.searchEmployeesBySkills(skills, userId);

        return ResponseEntity.ok(result);
    }

    // GET /api/search/{id}
    // Get query record
    @GetMapping("/{id}")
    public ResponseEntity<SearchQueryRecord> getQueryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                searchQueryService.getQueryById(id)
        );
    }

    // GET /api/search/user/{userId}
    // List past queries for user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SearchQueryRecord>> getQueriesForUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                searchQueryService.getQueriesForUser(userId)
        );
    }
}
