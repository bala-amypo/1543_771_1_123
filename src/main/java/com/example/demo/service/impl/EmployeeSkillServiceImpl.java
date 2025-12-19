package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeSkill;
import com.example.demo.model.Skill;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.EmployeeSkillRepository;
import com.example.demo.repository.SkillRepository;
import com.example.demo.service.EmployeeSkillService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EmployeeSkillServiceImpl implements EmployeeSkillService {

    private static final Set<String> VALID_PROFICIENCIES =
            Set.of("Beginner", "Intermediate", "Advanced", "Expert");

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
    public EmployeeSkill createEmployeeSkill(EmployeeSkill employeeSkill) {
        validate(employeeSkill);
        return employeeSkillRepository.save(employeeSkill);
    }

    @Override
    public EmployeeSkill updateEmployeeSkill(Long id, EmployeeSkill employeeSkill) {

        EmployeeSkill existing = employeeSkillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EmployeeSkill not found"));

        validate(employeeSkill);

        existing.setEmployee(employeeSkill.getEmployee());
        existing.setSkill(employeeSkill.getSkill());
        existing.setProficiencyLevel(employeeSkill.getProficiencyLevel());
        existing.setYearsOfExperience(employeeSkill.getYearsOfExperience());

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
                .orElseThrow(() -> new ResourceNotFoundException("EmployeeSkill not found"));

        employeeSkill.setActive(false);
        employeeSkillRepository.save(employeeSkill);
    }

    // 🔐 Validation logic
    private void validate(EmployeeSkill employeeSkill) {

        if (employeeSkill.getYearsOfExperience() == null ||
                employeeSkill.getYearsOfExperience() < 0) {
            throw new IllegalArgumentException("Experience years");
        }

        if (!VALID_PROFICIENCIES.contains(employeeSkill.getProficiencyLevel())) {
            throw new IllegalArgumentException("Invalid proficiency");
        }

        Employee employee = employeeRepository.findById(
                employeeSkill.getEmployee().getId()
        ).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (!employee.isActive()) {
            throw new IllegalArgumentException("inactive employee");
        }

        Skill skill = skillRepository.findById(
                employeeSkill.getSkill().getId()
        ).orElseThrow(() -> new ResourceNotFoundException("Skill not found"));

        if (!skill.isActive()) {
            throw new IllegalArgumentException("inactive skill");
        }
    }
}
