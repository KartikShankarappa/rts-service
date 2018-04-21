package com.dewpoint.rts.dto;

import io.swagger.annotations.ApiModelProperty;

public class UserRequestDTO {

    @ApiModelProperty(notes = "Provide user full name", required = false)
    private String userFullName;

    @ApiModelProperty(notes = "Provide user id", required = true)
    private String userId;

    @ApiModelProperty(notes = "Provide user password")
    private String password;

    @ApiModelProperty(notes = "Provide user role")
    private String role;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRequestDTO{" +
                "userFullName='" + userFullName + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
