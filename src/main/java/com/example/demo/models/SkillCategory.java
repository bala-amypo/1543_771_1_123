package com.example.demo;
public class SkillCategory {
    private long id;
    private String categoryName;
    private String description;
    private Boolean active;
    public SkillCategory(String categoryName, String description, Boolean active) {
        this.categoryName = categoryName;
        this.description = description;
        this.active = active;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public long getId() {
        return id;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public String getDescription() {
        return description;
    }
    public Boolean getActive() {
        return active;
    }
}