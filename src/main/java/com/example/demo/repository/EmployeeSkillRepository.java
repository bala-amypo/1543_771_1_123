package com.example.demo.repository;

import com.example.demo.model.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, Long> {

    List<EmployeeSkill> findByEmployee_IdAndActiveTrue(Long employeeId);
    List<EmployeeSkill> findBySkill_IdAndActiveTrue(Long skillId);
}
