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

    public EmployeeSkillServiceImpl(
            EmployeeSkillRepository employeeSkillRepository,
            EmployeeRepository employeeRepository,
            SkillRepository skillRepository
    ) {
        this.employeeSkillRepository = employeeSkillRepository;
        this.employeeRepository = employeeRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public EmployeeSkill createEmployeeSkill(EmployeeSkill mapping) {

        validate(mapping);

        mapping.setActive(true);
        return employeeSkillRepository.save(mapping);
    }

    @Override
    public EmployeeSkill updateEmployeeSkill(Long id, EmployeeSkill mapping) {

        EmployeeSkill existing = employeeSkillRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("EmployeeSkill not found")
                );

        validate(mapping);

        existing.setEmployee(mapping.getEmployee());
        existing.setSkill(mapping.getSkill());
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
        EmployeeSkill es = employeeSkillRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("EmployeeSkill not found")
                );

        es.setActive(false);
        employeeSkillRepository.save(es);
    }


    private void validate(EmployeeSkill mapping) {

        if (mapping.getYearsOfExperience() == null ||
                mapping.getYearsOfExperience() < 0) {
            throw new IllegalArgumentException("Experience years");
        }

        if (!VALID_PROFICIENCIES.contains(mapping.getProficiencyLevel())) {
            throw new IllegalArgumentException("Invalid proficiency");
        }

        Employee employee = employeeRepository.findById(
                mapping.getEmployee().getId()
        ).orElseThrow(() ->
                new ResourceNotFoundException("Employee not found")
        );

        if (!Boolean.TRUE.equals(employee.getActive())) {
            throw new IllegalArgumentException("inactive employee");
        }

        Skill skill = skillRepository.findById(
                mapping.getSkill().getId()
        ).orElseThrow(() ->
                new ResourceNotFoundException("Skill not found")
        );

        if (!Boolean.TRUE.equals(skill.getActive())) {
            throw new IllegalArgumentException("inactive skill");
        }
    }
}
