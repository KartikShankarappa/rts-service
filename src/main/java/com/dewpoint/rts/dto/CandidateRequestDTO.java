package com.dewpoint.rts.dto;

import java.math.BigDecimal;
import java.util.Date;

public class CandidateRequestDTO {

    private String applicationStatus;
    private BigDecimal billRate;
    private String currentJobTitle;
    private Date dateAvailable;
    private String email;
    private String firstName;
    private String lastJobTitle;
    private String lastName;
    private String phoneNumber;
    private String skills;
    private String source;
    private String clientName;
    private String clientCity;
    private String clientState;
    private String clientZip;

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public BigDecimal getBillRate() {
        return billRate;
    }

    public void setBillRate(BigDecimal billRate) {
        this.billRate = billRate;
    }

    public String getCurrentJobTitle() {
        return currentJobTitle;
    }

    public void setCurrentJobTitle(String currentJobTitle) {
        this.currentJobTitle = currentJobTitle;
    }

    public Date getDateAvailable() {
        return dateAvailable;
    }

    public void setDateAvailable(Date dateAvailable) {
        this.dateAvailable = dateAvailable;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastJobTitle() {
        return lastJobTitle;
    }

    public void setLastJobTitle(String lastJobTitle) {
        this.lastJobTitle = lastJobTitle;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    @Override
    public String toString() {
        return "CandidateRequestDTO{" +
                "applicationStatus='" + applicationStatus + '\'' +
                ", billRate=" + billRate +
                ", currentJobTitle='" + currentJobTitle + '\'' +
                ", dateAvailable=" + dateAvailable +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastJobTitle='" + lastJobTitle + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", skills='" + skills + '\'' +
                ", source='" + source + '\'' +
                ", clientName='" + clientName + '\'' +
                ", clientCity='" + clientCity + '\'' +
                ", clientState='" + clientState + '\'' +
                ", clientZip='" + clientZip + '\'' +
                '}';
    }
}
