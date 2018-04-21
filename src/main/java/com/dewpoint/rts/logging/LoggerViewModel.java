package com.dewpoint.rts.logging;

import ch.qos.logback.classic.Logger;

public class LoggerViewModel {
    private String name;
    private String level;

    public LoggerViewModel() {
        // Empty public constructor used by Jackson.
    }

    public LoggerViewModel(Logger logger) {
        this.name = logger.getName();
        this.level = logger.getEffectiveLevel().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "LoggerViewModel{" +
                "name='" + name + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
