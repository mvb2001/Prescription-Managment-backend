package com.example.Medical.DTO;

public record LoginRequest(
        String email,
        String password
) {}