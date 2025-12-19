package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "employee_skills")
public class EmployeeSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Skill skill;

    private String proficiencyLevel;
    private Integer yearsOfExperience;
    private boolean active = true;

    public Long getId() { return id; }
    public Employee getEmployee() { return employee; }
    public Skill getSkill() { return skill; }
    public String getProficiencyLevel() { return proficiencyLevel; }
    public Integer getYearsOfExperience() { return yearsOfExperience; }
    public boolean isActive() { return active; }

    public void setId(Long id) { this.id = id; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public void setSkill(Skill skill) { this.skill = skill; }
    public void setProficiencyLevel(String proficiencyLevel) { this.proficiencyLevel = proficiencyLevel; }
    public void setYearsOfExperience(Integer yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }
    public void setActive(boolean active) { this.active = active; }
}
