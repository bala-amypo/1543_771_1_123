package com.example.demo.service.impl;

import com.example.demo.model.SkillCategory;
import com.example.demo.repository.SkillCategoryRepository;
import com.example.demo.service.SkillCategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SkillCategoryServiceImpl implements SkillCategoryService {

    private final SkillCategoryRepository skillCategoryRepository;

    public SkillCategoryServiceImpl(SkillCategoryRepository skillCategoryRepository) {
        this.skillCategoryRepository = skillCategoryRepository;
    }

    @Override
    public SkillCategory createCategory(SkillCategory category) {

        if (skillCategoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new IllegalArgumentException("Category name must be unique");
        }

        category.setActive(true);
        return skillCategoryRepository.save(category);
    }

    @Override
    public SkillCategory updateCategory(Long id, SkillCategory category) {

        SkillCategory existing = skillCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SkillCategory not found"));

        if (!existing.getCategoryName().equals(category.getCategoryName())
                && skillCategoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new IllegalArgumentException("Category name must be unique");
        }

        existing.setCategoryName(category.getCategoryName());
        existing.setDescription(category.getDescription());

        return skillCategoryRepository.save(existing);
    }

    @Override
    public SkillCategory getCategoryById(Long id) {
        return skillCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SkillCategory not found"));
    }

    @Override
    public List<SkillCategory> getAllCategories() {
        return skillCategoryRepository.findAll();
    }

    @Override
    public void deactivateCategory(Long id) {

        SkillCategory category = skillCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SkillCategory not found"));

        category.setActive(false);
        skillCategoryRepository.save(category);
    }
}
