package com.piisw.kino.rest.exceptions.impl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.piisw.kino.rest.exceptions.CodeEntityNotFoundException;
import com.piisw.kino.rest.exceptions.DataValidationException;
import com.piisw.kino.rest.exceptions.EntityNotFoundException;
import com.piisw.kino.rest.exceptions.InvalidTicketException;
import com.piisw.kino.rest.exceptions.impl.ErrorBase.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException exception) {
        return exception.getObject();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataValidationException.class)
    public ErrorResponse handleDataValidationException(DataValidationException exception) {
        return exception.getObject();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CodeEntityNotFoundException.class)
    public ErrorResponse handleCodeEntityNotFoundException(CodeEntityNotFoundException exception) {
        return exception.getObject();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTicketException.class)
    public ErrorResponse handleInvalidTicketException(InvalidTicketException exception) {
        return exception.getObject();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException exception) {
        return new ErrorResponse("The username or password is incorrect");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException exception) {
        return new ErrorResponse("You are not authorized to access this resource");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccountStatusException.class)
    public ErrorResponse handleAccountStatusException(AccountStatusException exception) {
        return new ErrorResponse("The account is locked");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(SignatureException.class)
    public ErrorResponse handleSignatureException(SignatureException exception) {
        return new ErrorResponse("The JWT signature is invalid");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ExpiredJwtException.class)
    public ErrorResponse handleExpiredJwtException(ExpiredJwtException exception) {
        return new ErrorResponse("The JWT token has expired");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleGenericError(RuntimeException exception) {
        return new ErrorResponse(exception.getMessage());
    }
}