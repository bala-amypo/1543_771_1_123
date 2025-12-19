package com.example.demo.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "search_query_records")
public class SearchQueryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long searcherId;

    @Column(nullable = false)
    private String skillsRequested;

    private Integer resultsCount;

    private Timestamp searchedAt;

    @PrePersist
    public void onCreate() {
        this.searchedAt = new Timestamp(System.currentTimeMillis());
    }


    public Long getId() {
        return id;
    }

    public Long getSearcherId() {
        return searcherId;
    }

    public String getSkillsRequested() {
        return skillsRequested;
    }

    public Integer getResultsCount() {
        return resultsCount;
    }

    public Timestamp getSearchedAt() {
        return searchedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSearcherId(Long searcherId) {
        this.searcherId = searcherId;
    }

    public void setSkillsRequested(String skillsRequested) {
        this.skillsRequested = skillsRequested;
    }

    public void setResultsCount(Integer resultsCount) {
        this.resultsCount = resultsCount;
    }

    public void setSearchedAt(Timestamp searchedAt) {
        this.searchedAt = searchedAt;
    }
}
