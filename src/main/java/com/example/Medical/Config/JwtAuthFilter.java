package com.example.Medical.Config;

import com.example.Medical.Repository.DoctorRepository;
import com.example.Medical.Repository.PharmacistRepository;
import com.example.Medical.Security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final DoctorRepository doctorRepository;
    private final PharmacistRepository pharmacistRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No auth header or invalid format for: " + request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String email = jwtService.extractEmail(jwt);
        final String role = jwtService.extractRole(jwt);
        
        System.out.println("JWT Filter - Email: " + email + ", Role: " + role + ", URI: " + request.getRequestURI());

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            switch (role.toUpperCase()) {
                case "DOCTOR":
                    doctorRepository.findByEmail(email).ifPresent(doctor -> {
                        if (jwtService.isTokenValid(jwt, doctor)) {
                            UsernamePasswordAuthenticationToken authToken =
                                    new UsernamePasswordAuthenticationToken(
                                            email,
                                            null,
                                            List.of(new SimpleGrantedAuthority("ROLE_DOCTOR"))
                                    );
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                            System.out.println("✅ Authentication set for DOCTOR: " + email);
                        } else {
                            System.out.println("❌ Token validation failed for DOCTOR: " + email);
                        }
                    });
                    if (doctorRepository.findByEmail(email).isEmpty()) {
                        System.out.println("❌ Doctor not found in DB: " + email);
                    }
                    break;

                case "PHARMACIST":
                    pharmacistRepository.findByEmail(email).ifPresent(pharmacist -> {
                        if (jwtService.isTokenValid(jwt, pharmacist)) {
                            UsernamePasswordAuthenticationToken authToken =
                                    new UsernamePasswordAuthenticationToken(
                                            email,
                                            null,
                                            List.of(new SimpleGrantedAuthority("ROLE_PHARMACIST"))
                                    );
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    });
                    break;

                default:
                   
                    break;
            }
        }

        filterChain.doFilter(request, response);
    }
}
