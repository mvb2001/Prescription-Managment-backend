package com.example.Medical.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRequest {
    private String nic;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String livingArea;
    private LocalDate dateOfBirth;
    private String email;
}
