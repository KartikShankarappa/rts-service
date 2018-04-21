package com.dewpoint.rts.dto;

import java.util.Date;

public class UserDTO {

    private String userFullName;
    private String userId;
    private String role;
    private String status;
    private String createdBy;
    private Date createdOn;
    private String lastModifiedBy;
    private Date lastModifiedOn;

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(Date lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userFullName='" + userFullName + '\'' +
                ", userId='" + userId + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdOn=" + createdOn +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedOn=" + lastModifiedOn +
                '}';
    }
}
