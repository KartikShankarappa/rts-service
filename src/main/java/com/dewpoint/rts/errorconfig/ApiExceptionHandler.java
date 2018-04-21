package com.dewpoint.rts.errorconfig;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler {

    public ApiExceptionHandler(){
    }

    @ExceptionHandler(MissingPermission.class)
    ResponseEntity handleApiExceptions(MissingPermission e){
        return error(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ApiOperationException.class)
    ResponseEntity handleApiExceptions(ApiOperationException e){
        return error(e, HttpStatus.MULTI_STATUS);
    }

    @ExceptionHandler(DataAccessException.class)
    ResponseEntity handleApiExceptions(DataAccessException e){
        return error(e, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Throwable.class)
    ResponseEntity handleGenericExceptions(Throwable e){
        ApiError apiError = new ApiError();
        apiError.setErrorCode("" + HttpStatus.SERVICE_UNAVAILABLE.value());
        apiError.setErrorReasonPhrase(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
        apiError.setErrorMessage(e.getMessage());
        return new ResponseEntity(apiError, HttpStatus.SERVICE_UNAVAILABLE);
   }

    public ResponseEntity error(Exception exception, HttpStatus httpStatus) {
        ApiError apiError = new ApiError();
        apiError.setErrorCode("" + httpStatus.value());
        apiError.setErrorReasonPhrase(httpStatus.getReasonPhrase());
        apiError.setErrorMessage(exception.getMessage());
        return new ResponseEntity(apiError, httpStatus);
    }
}
