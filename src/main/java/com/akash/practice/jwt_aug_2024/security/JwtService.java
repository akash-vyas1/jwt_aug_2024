package com.akash.practice.jwt_aug_2024.security;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {

    public String getJwtToken(String email, List<String> roles) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .issuer("Akash Vyas org.")
                .subject(email)
                .expiration(
                        new Date(System.currentTimeMillis() + MySecurityConstants.getJwtTokenValidityTime()))
                .claim("email", email)
                .claim("roles", StringUtils.join(roles, ','))
                .signWith(MySecurityConstants.getKey())
                .compact();
    }

    public String extractUserEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public String extractRoles(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(MySecurityConstants.getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractUserEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

}
