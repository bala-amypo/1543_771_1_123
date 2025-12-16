package com.example.demo;

import java.security.Timestamp;

public class SearchQueryRecord {
    private long id;
    private long searcherld;
    private String skillsRequested;
    private Integer Resultscount;
    private Timestamp searchedAt;
    public SearchQueryRecord(long searcherld, String skillsRequested, Integer resultscount, Timestamp searchedAt) {
        this.searcherld = searcherld;
        this.skillsRequested = skillsRequested;
        Resultscount = resultscount;
        this.searchedAt = searchedAt;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setSearcherld(long searcherld) {
        this.searcherld = searcherld;
    }
    public void setSkillsRequested(String skillsRequested) {
        this.skillsRequested = skillsRequested;
    }
    public void setResultscount(Integer resultscount) {
        Resultscount = resultscount;
    }
    public void setSearchedAt(Timestamp searchedAt) {
        this.searchedAt = searchedAt;
    }
    public long getId() {
        return id;
    }
    public long getSearcherld() {
        return searcherld;
    }
    public String getSkillsRequested() {
        return skillsRequested;
    }
    public Integer getResultscount() {
        return Resultscount;
    }
    public Timestamp getSearchedAt() {
        return searchedAt;
    }
}
