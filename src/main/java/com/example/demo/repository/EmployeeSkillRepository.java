package com.example.demo.repository;

import com.example.demo.model.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, Long> {

    // Fetch all active skills of an employee
    List<EmployeeSkill> findByEmployeeIdAndActiveTrue(Long employeeId);

    // Fetch all employees having a particular skill
    List<EmployeeSkill> findBySkillIdAndActiveTrue(Long skillId);

    // Fetch by proficiency level
    List<EmployeeSkill> findByProficiencyLevel(String proficiencyLevel);
}
