package com.akash.practice.jwt_aug_2024.controllers;

import java.time.LocalTime;
import java.util.Date;
import java.util.stream.Collectors;
import java.time.*;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.akash.practice.jwt_aug_2024.repositories.UserRepo;
import com.akash.practice.jwt_aug_2024.security.JwtService;
import com.akash.practice.jwt_aug_2024.security.MySecurityConstants;
import com.akash.practice.jwt_aug_2024.utilities.exceptionHandling.UserNotAuthenticatedException;

import io.jsonwebtoken.Jwts;

@RestController
public class UserAuthController {

    @Autowired
    AuthenticationManager authManager;
    @Autowired
    UserRepo userRepo;
    @Autowired
    JwtService jwtService;

    @PostMapping("/login")
    public String login(@RequestBody LoginDetails user) throws UserNotAuthenticatedException {

        // System.out.println("inside login method");
        // System.out.println("User with email : " + user.email());

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.email(), user.password()));
        if (authentication.isAuthenticated()) {
            // System.out.println("User authenticated");
            return jwtService.getJwtToken(user.email(), userRepo.findByEmail(user.email()).get().getRolesList());
        } else {
            // System.out.println("user not authenticated");
            throw new UserNotAuthenticatedException("User not authenticated.");
        }
    }

}
