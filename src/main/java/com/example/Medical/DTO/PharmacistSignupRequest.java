package com.example.Medical.DTO;

public record PharmacistSignupRequest(
        String nic,
        String firstName,
        String lastName,
        String email,
        String password,
        String contact
) {}
