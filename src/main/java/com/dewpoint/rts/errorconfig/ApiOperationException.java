package com.dewpoint.rts.errorconfig;

public class ApiOperationException extends RuntimeException {

    public ApiOperationException(String message){
        super(message);
    }
}
