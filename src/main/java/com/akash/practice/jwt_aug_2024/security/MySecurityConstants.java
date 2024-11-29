package com.akash.practice.jwt_aug_2024.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

// import java.time.Duration;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

@Component
public class MySecurityConstants {

    private MySecurityConstants() {
    }

    public static final String SECRET = "abcdefghijklmnopqrstuvwxyz_abcdefghijklmnopqrstuvwxyz_1234567890";
    // private static Duration jwtTokenValidityTime = Duration.ofMinutes(30);

    private static SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public static SecretKey getKey() {
        return key;
    }

    private static int jwtTokenValidityTime1 = 1000 * 60 * 10; //
    // 1 second * 1 minutes * 30 minutes

    public static void setJwtTokenValidityTime(int minutes) {
        jwtTokenValidityTime1 = 1000 * minutes;
    }

    public static int getJwtTokenValidityTime() {
        return jwtTokenValidityTime1;
    }

    // public static Duration getJwtValidityTime() {
    // return jwtTokenValidityTime;
    // }

    // public static void setJwtDuration(int minutes) {
    // jwtTokenValidityTime = Duration.ofMinutes(minutes);
    // }

}
