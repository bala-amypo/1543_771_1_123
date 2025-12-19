package com.example.demo.service.impl;

import com.example.demo.model.EmployeeSkill;
import com.example.demo.repository.EmployeeSkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchQueryServiceImpl {

    private final EmployeeSkillRepository employeeSkillRepository;

    public SearchQueryServiceImpl(EmployeeSkillRepository employeeSkillRepository) {
        this.employeeSkillRepository = employeeSkillRepository;
    }

    public List<EmployeeSkill> searchByEmployeeId(Long employeeId) {
        return employeeSkillRepository
                .findByEmployee_IdAndActiveTrue(employeeId);
    }

    public List<EmployeeSkill> searchBySkillId(Long skillId) {
        return employeeSkillRepository
                .findBySkill_IdAndActiveTrue(skillId);
    }
}
