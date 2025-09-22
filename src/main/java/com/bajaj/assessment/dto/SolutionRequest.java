package com.bajaj.assessment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SolutionRequest {
    @JsonProperty("finalQuery")
    private String finalQuery;

    // Constructors
    public SolutionRequest() {}

    public SolutionRequest(String finalQuery) {
        this.finalQuery = finalQuery;
    }

    // Getters and Setters
    public String getFinalQuery() {
        return finalQuery;
    }

    public void setFinalQuery(String finalQuery) {
        this.finalQuery = finalQuery;
    }
}
