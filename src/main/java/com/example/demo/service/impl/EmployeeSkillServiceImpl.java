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
    public EmployeeSkill create(Long employeeId,
                                Long skillId,
                                String proficiencyLevel,
                                Integer yearsOfExperience) {

        if (yearsOfExperience == null || yearsOfExperience < 0) {
            throw new IllegalArgumentException("Experience years");
        }

        if (!VALID_PROFICIENCIES.contains(proficiencyLevel)) {
            throw new IllegalArgumentException("Invalid proficiency");
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (!employee.isActive()) {
            throw new IllegalArgumentException("inactive employee");
        }

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));

        if (!skill.getActive()) {
            throw new IllegalArgumentException("inactive skill");
        }

        EmployeeSkill employeeSkill = new EmployeeSkill();
        employeeSkill.setEmployee(employee);
        employeeSkill.setSkill(skill);
        employeeSkill.setProficiencyLevel(proficiencyLevel);
        employeeSkill.setYearsOfExperience(yearsOfExperience);
        employeeSkill.setActive(true);

        return employeeSkillRepository.save(employeeSkill);
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
                .orElseThrow(() -> new ResourceNotFoundException("EmployeeSkill not found"));

        es.setActive(false);
        employeeSkillRepository.save(es);
    }
}
