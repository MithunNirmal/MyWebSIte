package com.mithunnirmal.merch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandlerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    ProblemDetail handleUserNotFoundException(UserNotFoundException e) {
        ProblemDetail problemDetail =  ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("User Not found");
        return problemDetail;
    }

    @ExceptionHandler(UserNotVerifiedException.class)
    ProblemDetail handleUserNotVerifiedException(UserNotVerifiedException e) {
        ProblemDetail problemDetail =  ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        problemDetail.setTitle("User Not verified");
        problemDetail.setProperty("UserOldVerificationToken", e.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(UserEmailTakenException.class)
    ProblemDetail handleUserEmailTakenException(UserNotVerifiedException e) {
        ProblemDetail problemDetail =  ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        problemDetail.setTitle("email taken");
        problemDetail.setProperty("UserOldVerificationToken", e.getMessage());
        return problemDetail;
    }



    @ExceptionHandler(BadCredentialsException.class)
    ProblemDetail handleBadCredentialsException(BadCredentialsException e) {
        ProblemDetail problemDetail =  ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setTitle("Incorrect password");
        return problemDetail;
    }
}
