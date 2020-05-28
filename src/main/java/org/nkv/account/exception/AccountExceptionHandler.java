package org.nkv.account.exception;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.nkv.account.domain.Error;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AccountExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Error invalidRequestHandler(InvalidRequestException e) {
        return new Error(HttpStatus.BAD_REQUEST, LocalDateTime.now(ZoneId.of("GMT+3")), e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Error entityNotFoundException(EntityNotFoundException e) {
        return new Error(HttpStatus.NOT_FOUND, LocalDateTime.now(ZoneId.of("GMT+3")), e.getMessage());
    }
}