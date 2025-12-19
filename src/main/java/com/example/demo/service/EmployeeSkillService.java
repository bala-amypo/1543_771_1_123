package com.example.demo.service;

import com.example.demo.model.EmployeeSkill;

import java.util.List;

public interface EmployeeSkillService {

    EmployeeSkill create(Long employeeId, Long skillId,
                         String proficiencyLevel, Integer yearsOfExperience);

    EmployeeSkill update(Long id, Long employeeId, Long skillId,
                         String proficiencyLevel, Integer yearsOfExperience);

    List<EmployeeSkill> getSkillsForEmployee(Long employeeId);

    List<EmployeeSkill> getEmployeesBySkill(Long skillId);

    void deactivateEmployeeSkill(Long id);
}
