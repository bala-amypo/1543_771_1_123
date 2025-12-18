package com.example.demo;
public class EmployeeSkill {
    private long id;
    private ManyToOne employee;
    private ManyToOne skill;
    private String proficiencySkill;
    private Integer yearsofExperience;
    private Boolean active;
    public EmployeeSkill(ManyToOne employee, ManyToOne skill, String proficiencySkill, Integer yearsofExperience,
            Boolean active) {
        this.employee = employee;
        this.skill
         = skill;
        this.proficiencySkill = proficiencySkill;
        this.yearsofExperience = yearsofExperience;
        this.active = active;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setEmployee(ManyToOne employee) {
        this.employee = employee;
    }
    public void setSkill(ManyToOne skill) {
        this.skill = skill;
    }
    public void setProficiencySkill(String proficiencySkill) {
        this.proficiencySkill = proficiencySkill;
    }
    public void setYearsofExperience(Integer yearsofExperience) {
        this.yearsofExperience = yearsofExperience;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public long getId() {
        return id;
    }
    public ManyToOne getEmployee() {
        return employee;
    }
    public ManyToOne getSkill() {
        return skill;
    }
    public String getProficiencySkill() {
        return proficiencySkill;
    }
    public Integer getYearsofExperience() {
        return yearsofExperience;
    }
    public Boolean getActive() {
        return active;
    }
}