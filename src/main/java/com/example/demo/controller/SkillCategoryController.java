package com.example.demo.controller;

import com.example.demo.model.SkillCategory;
import com.example.demo.service.SkillCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skill-categories")
public class SkillCategoryController {

    private final SkillCategoryService skillCategoryService;

    public SkillCategoryController(SkillCategoryService skillCategoryService) {
        this.skillCategoryService = skillCategoryService;
    }

    // POST /api/skill-categories
    // Create category
    @PostMapping
    public ResponseEntity<SkillCategory> createCategory(
            @RequestBody SkillCategory category) {

        SkillCategory saved = skillCategoryService.createCategory(category);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // PUT /api/skill-categories/{id}
    // Update category
    @PutMapping("/{id}")
    public ResponseEntity<SkillCategory> updateCategory(
            @PathVariable Long id,
            @RequestBody SkillCategory category) {

        SkillCategory updated =
                skillCategoryService.updateCategory(id, category);
        return ResponseEntity.ok(updated);
    }

    // GET /api/skill-categories/{id}
    // Get category by id
    @GetMapping("/{id}")
    public ResponseEntity<SkillCategory> getCategoryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                skillCategoryService.getCategoryById(id)
        );
    }

    // GET /api/skill-categories
    // List all categories
    @GetMapping
    public ResponseEntity<List<SkillCategory>> getAllCategories() {
        return ResponseEntity.ok(
                skillCategoryService.getAllCategories()
        );
    }

    // PUT /api/skill-categories/{id}/deactivate
    // Deactivate category
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateCategory(
            @PathVariable Long id) {

        skillCategoryService.deactivateCategory(id);
        return ResponseEntity.noContent().build();
    }
}
