package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeSkill;
import com.example.demo.model.Skill;
import com.example.demo.service.EmployeeSkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employee-skills")
public class EmployeeSkillController {

    private final EmployeeSkillService employeeSkillService;

    public EmployeeSkillController(EmployeeSkillService employeeSkillService) {
        this.employeeSkillService = employeeSkillService;
    }

    // POST /api/employee-skills
    // Create mapping
    @PostMapping
    public ResponseEntity<EmployeeSkill> createEmployeeSkill(
            @RequestBody Map<String, Object> payload) {

        EmployeeSkill mapping = buildEmployeeSkillFromPayload(payload);

        EmployeeSkill saved =
                employeeSkillService.createEmployeeSkill(mapping);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // PUT /api/employee-skills/{id}
    // Update mapping
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeSkill> updateEmployeeSkill(
            @PathVariable Long id,
            @RequestBody EmployeeSkill mapping) {

        EmployeeSkill updated =
                employeeSkillService.updateEmployeeSkill(id, mapping);

        return ResponseEntity.ok(updated);
    }

    // GET /api/employee-skills/employee/{employeeId}
    // List skills for employee
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeSkill>> getSkillsForEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                employeeSkillService.getSkillsForEmployee(employeeId)
        );
    }

    // GET /api/employee-skills/skill/{skillId}
    // List employees with skill
    @GetMapping("/skill/{skillId}")
    public ResponseEntity<List<EmployeeSkill>> getEmployeesBySkill(
            @PathVariable Long skillId) {

        return ResponseEntity.ok(
                employeeSkillService.getEmployeesBySkill(skillId)
        );
    }

    // PUT /api/employee-skills/{id}/deactivate
    // Deactivate mapping
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateEmployeeSkill(
            @PathVariable Long id) {

        employeeSkillService.deactivateEmployeeSkill(id);
        return ResponseEntity.noContent().build();
    }

    // -------- Helper method --------

    private EmployeeSkill buildEmployeeSkillFromPayload(
            Map<String, Object> payload) {

        Employee employee = new Employee();
        employee.setId(
                Long.parseLong(payload.get("employeeId").toString())
        );

        Skill skill = new Skill();
        skill.setId(
                Long.parseLong(payload.get("skillId").toString())
        );

        EmployeeSkill mapping = new EmployeeSkill();
        mapping.setEmployee(employee);
        mapping.setSkill(skill);
        mapping.setProficiencyLevel(
                payload.get("proficiencyLevel").toString()
        );
        mapping.setYearsOfExperience(
                Integer.parseInt(
                        payload.get("yearsOfExperience").toString()
                )
        );

        return mapping;
    }
}
