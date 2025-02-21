package com.ms.user.configs;

import com.ms.user.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
    var token = recoverToken(request);
    if (token != null) {
        var login = tokenService.validateToken(token);
        System.out.println("Login from token: " + login);
        if (login != null) {
            UserDetails user = userRepository.findByLogin(login);
            System.out.println("User: " + (user != null ? user.getUsername() : "null"));
            System.out.println("Authorities: " + (user != null ? user.getAuthorities() : "null"));
            if (user != null) {
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Authentication set: " + authentication);
            } else {
                System.out.println("User not found for login: " + login);
            }
        } else {
            System.out.println("Invalid token");
        }
    } else {
        System.out.println("No token provided");
    }
    filterChain.doFilter(request, response);
}

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
