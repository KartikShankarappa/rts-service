package com.dewpoint.rts.dto;

public class CandidateSearchRequestDTO {

    private String resumeKeywords;
    private String skills;
    private String status;
    private String clientName;
    private String clientCity;
    private String clientState;
    private String clientZip;
    private String source;

    public String getResumeKeywords() {
        return resumeKeywords;
    }

    public void setResumeKeywords(String resumeKeywords) {
        this.resumeKeywords = resumeKeywords;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientCity() {
        return clientCity;
    }

    public void setClientCity(String clientCity) {
        this.clientCity = clientCity;
    }

    public String getClientState() {
        return clientState;
    }

    public void setClientState(String clientState) {
        this.clientState = clientState;
    }

    public String getClientZip() {
        return clientZip;
    }

    public void setClientZip(String clientZip) {
        this.clientZip = clientZip;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "CandidateSearchRequestDTO{" +
                "resumeKeywords='" + resumeKeywords + '\'' +
                ", skills='" + skills + '\'' +
                ", status='" + status + '\'' +
                ", clientName='" + clientName + '\'' +
                ", clientCity='" + clientCity + '\'' +
                ", clientState='" + clientState + '\'' +
                ", clientZip='" + clientZip + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
