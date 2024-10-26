package com.akash.practice.jwt_aug_2024.utilities.exceptionHandling;

import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.jersey.JerseyProperties.Servlet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { Exception.class, RoleNotFoundException.class, UsernameNotFoundException.class,
            UserNotAuthenticatedException.class, ServletException.class, ExpiredJwtException.class,
            NoPermissionException.class })
    public final ResponseEntity<ExceptionDetails> handleGeneralException(Exception e, WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(e.getMessage(), LocalDateTime.now(),
                request.getDescription(true));
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

}
