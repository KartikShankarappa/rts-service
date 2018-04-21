package com.dewpoint.rts.errorconfig;

import org.springframework.stereotype.Component;

@Component
public class ApiError {

    private String errorCode;
    private String errorReasonPhrase;
    private String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorReasonPhrase() {
        return errorReasonPhrase;
    }

    public void setErrorReasonPhrase(String errorReasonPhrase) {
        this.errorReasonPhrase = errorReasonPhrase;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "errorCode='" + errorCode + '\'' +
                ", errorReasonPhrase='" + errorReasonPhrase + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
