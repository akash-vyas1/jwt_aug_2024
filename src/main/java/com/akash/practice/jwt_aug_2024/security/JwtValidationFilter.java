package com.akash.practice.jwt_aug_2024.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtValidationFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = "";
        String userEmail = "";
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            userEmail = jwtService.extractUserEmail(token);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails ud = context.getBean(MyUserDetailsService.class).loadUserByUsername(userEmail);

                if (jwtService.validateToken(token, ud)) {
                    UsernamePasswordAuthenticationToken upAT = new UsernamePasswordAuthenticationToken(userEmail, null,
                            ud.getAuthorities());

                    upAT.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(upAT);
                    filterChain.doFilter(request, response);
                }

            } else {
                System.out.println("User already authenticated");
            }

        } else {
            throw new ServletException("Authenticate details not available");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (request.getRequestURI().equals("/login")) {
            return true;
        } else {
            return false;
        }
    }

    // private List<GrantedAuthority> getAuthorities(String roles) {
    // return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
    // }

}
