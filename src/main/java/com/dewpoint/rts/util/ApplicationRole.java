package com.dewpoint.rts.util;

public enum ApplicationRole {

    ADMINISTRATOR("Administrator", "Administrator"),
    USER("User", "User"),
    CANDIDATE("Candidate", "Candidate");

    private final String key;
    private final String value;

    ApplicationRole(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
