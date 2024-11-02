package com.reacconmind.reacconmind.model;


public class ModerationResult {
    private ModerationType decision;
    private String details;

    public ModerationType getDecision() {
        return decision;
    }

    public void setDecision(ModerationType decision) {
        this.decision = decision;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}