package com.example.Medical.DTO;


public record AuthResponse(
        String token,
        String role
) {}