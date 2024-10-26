package com.akash.practice.jwt_aug_2024.utilities.exceptionHandling;

public class UserNotAuthenticatedException extends Exception {
    public UserNotAuthenticatedException(String e) {
        super(e);
    }
}