package com.dewpoint.rts.util;

public enum ApplicationStatus {

    HIRED("HIRED", "Hired"),
    NEVER_INTERVIEWED("NEVER_INTERVIEWED", "Never Interviewed"),
    INTERVIEWED_FUTURE("INTERVIEWED_FUTURE", "Interviewed - Future"),
    INTERVIEWED_NOT_A_FIT("INTERVIEWED_NOT_A_FIT", "Interviewed - Not a fit");

    private final String key;
    private final String value;

    ApplicationStatus(String key, String value){
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
