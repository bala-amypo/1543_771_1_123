package com.example.demo.service.impl;

import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeSkill;
import com.example.demo.model.Skill;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.EmployeeSkillRepository;
import com.example.demo.repository.SkillRepository;
import com.example.demo.service.EmployeeSkillService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeSkillServiceImpl implements EmployeeSkillService {

    private final EmployeeSkillRepository employeeSkillRepository;
    private final EmployeeRepository employeeRepository;
    private final SkillRepository skillRepository;

    public EmployeeSkillServiceImpl(EmployeeSkillRepository employeeSkillRepository,
                                    EmployeeRepository employeeRepository,
                                    SkillRepository skillRepository) {
        this.employeeSkillRepository = employeeSkillRepository;
        this.employeeRepository = employeeRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public EmployeeSkill createEmployeeSkill(EmployeeSkill mapping) {

        validateProficiency(mapping.getProficiencyLevel());
        validateExperience(mapping.getYearsOfExperience());

        Employee employee = employeeRepository.findById(mapping.getEmployee().getId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        Skill skill = skillRepository.findById(mapping.getSkill().getId())
                .orElseThrow(() -> new EntityNotFoundException("Skill not found"));

        if (!Boolean.TRUE.equals(employee.getActive())) {
            throw new IllegalStateException("inactive employee");
        }

        if (!Boolean.TRUE.equals(skill.getActive())) {
            throw new IllegalStateException("inactive skill");
        }

        mapping.setEmployee(employee);
        mapping.setSkill(skill);
        mapping.setActive(true);

        return employeeSkillRepository.save(mapping);
    }

    @Override
    public EmployeeSkill updateEmployeeSkill(Long id, EmployeeSkill mapping) {

        EmployeeSkill existing = employeeSkillRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EmployeeSkill not found"));

        validateProficiency(mapping.getProficiencyLevel());
        validateExperience(mapping.getYearsOfExperience());

        existing.setProficiencyLevel(mapping.getProficiencyLevel());
        existing.setYearsOfExperience(mapping.getYearsOfExperience());

        return employeeSkillRepository.save(existing);
    }

    @Override
    public List<EmployeeSkill> getSkillsForEmployee(Long employeeId) {
        return employeeSkillRepository.findByEmployeeIdAndActiveTrue(employeeId);
    }

    @Override
    public List<EmployeeSkill> getEmployeesBySkill(Long skillId) {
        return employeeSkillRepository.findBySkillIdAndActiveTrue(skillId);
    }

    @Override
    public void deactivateEmployeeSkill(Long id) {
        EmployeeSkill employeeSkill = employeeSkillRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EmployeeSkill not found"));

        employeeSkill.setActive(false);
        employeeSkillRepository.save(employeeSkill);
    }

    // ---------- Private validation helpers ----------

    private void validateProficiency(String level) {
        if (!List.of("Beginner", "Intermediate", "Advanced", "Expert").contains(level)) {
            throw new IllegalArgumentException(
                    "proficiencyLevel must be Beginner, Intermediate, Advanced, or Expert"
            );
        }
    }

    private void validateExperience(Integer years) {
        if (years == null || years < 0) {
            throw new IllegalArgumentException("yearsOfExperience must be >= 0");
        }
    }
}
