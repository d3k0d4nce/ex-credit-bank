package ru.kishko.calculator.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CreditExceptionHandler {

    @ExceptionHandler(value = {CreditException.class})
    public ResponseEntity<Object> handleProjectNotFoundException(CreditException creditException) {

        log.error("Credit exception occurred: ", creditException); // Логирование ошибки

        Exception exception = new Exception(
                creditException.getMessage(),
                creditException.getCause(),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(exception, exception.getHttpStatus());

    }

}
