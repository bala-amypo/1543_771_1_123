package com.example.demo.controller;

import com.example.demo.model.EmployeeSkill;
import com.example.demo.service.EmployeeSkillService;
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

    // CREATE EmployeeSkill
    @PostMapping
    public EmployeeSkill create(@RequestBody Map<String, Object> payload) {

        Long employeeId = Long.valueOf(payload.get("employeeId").toString());
        Long skillId = Long.valueOf(payload.get("skillId").toString());
        String proficiencyLevel = payload.get("proficiencyLevel").toString();
        Integer yearsOfExperience =
                Integer.valueOf(payload.get("yearsOfExperience").toString());

        return employeeSkillService.create(
                employeeId,
                skillId,
                proficiencyLevel,
                yearsOfExperience
        );
    }

    // UPDATE EmployeeSkill
    @PutMapping("/{id}")
    public EmployeeSkill update(@PathVariable Long id,
                                @RequestBody Map<String, Object> payload) {

        Long employeeId = Long.valueOf(payload.get("employeeId").toString());
        Long skillId = Long.valueOf(payload.get("skillId").toString());
        String proficiencyLevel = payload.get("proficiencyLevel").toString();
        Integer yearsOfExperience =
                Integer.valueOf(payload.get("yearsOfExperience").toString());

        return employeeSkillService.update(
                id,
                employeeId,
                skillId,
                proficiencyLevel,
                yearsOfExperience
        );
    }

    // GET skills for an employee
    @GetMapping("/employee/{employeeId}")
    public List<EmployeeSkill> getByEmployee(@PathVariable Long employeeId) {
        return employeeSkillService.getSkillsForEmployee(employeeId);
    }

    // GET employees by skill
    @GetMapping("/skill/{skillId}")
    public List<EmployeeSkill> getBySkill(@PathVariable Long skillId) {
        return employeeSkillService.getEmployeesBySkill(skillId);
    }

    // DEACTIVATE mapping
    @PutMapping("/{id}/deactivate")
    public void deactivate(@PathVariable Long id) {
        employeeSkillService.deactivateEmployeeSkill(id);
    }
}
