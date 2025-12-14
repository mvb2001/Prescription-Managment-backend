package com.example.Medical.DTO;

public record DoctorSignupRequest(
        String nic,
        String firstName,
        String lastName,
        String email,
        String password,
        String contact,
        String mbbsUniversity,
        String speciality
) {}
