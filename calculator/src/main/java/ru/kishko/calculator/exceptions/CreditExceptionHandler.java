package ru.kishko.calculator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CreditExceptionHandler {

    @ExceptionHandler(value = {CreditException.class})
    public ResponseEntity<Object> handleProjectNotFoundException(CreditException creditException) {

        Exception exception = new Exception(
                creditException.getMessage(),
                creditException.getCause(),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(exception, exception.getHttpStatus());

    }

}
