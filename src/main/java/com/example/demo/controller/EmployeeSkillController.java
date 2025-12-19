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

    @PostMapping
    public EmployeeSkill create(@RequestBody Map<String, Object> payload) {

        if (!payload.containsKey("employeeId")
                || !payload.containsKey("skillId")
                || !payload.containsKey("proficiencyLevel")
                || !payload.containsKey("yearsOfExperience")) {

            throw new IllegalArgumentException(
                    "Required fields: employeeId, skillId, proficiencyLevel, yearsOfExperience"
            );
        }

        Long employeeId = Long.parseLong(payload.get("employeeId").toString());
        Long skillId = Long.parseLong(payload.get("skillId").toString());
        String proficiencyLevel = payload.get("proficiencyLevel").toString();
        Integer yearsOfExperience =
                Integer.parseInt(payload.get("yearsOfExperience").toString());

        return employeeSkillService.create(
                employeeId,
                skillId,
                proficiencyLevel,
                yearsOfExperience
        );
    }

    @GetMapping("/employee/{employeeId}")
    public List<EmployeeSkill> getByEmployee(@PathVariable Long employeeId) {
        return employeeSkillService.getSkillsForEmployee(employeeId);
    }

    @GetMapping("/skill/{skillId}")
    public List<EmployeeSkill> getBySkill(@PathVariable Long skillId) {
        return employeeSkillService.getEmployeesBySkill(skillId);
    }

    @PutMapping("/{id}/deactivate")
    public void deactivate(@PathVariable Long id) {
        employeeSkillService.deactivateEmployeeSkill(id);
    }
}
