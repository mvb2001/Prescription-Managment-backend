package com.example.Medical.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "patient", uniqueConstraints = {@UniqueConstraint(columnNames = "nic")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nic; // optional for children

    private String firstName;
    private String lastName;
    private String contactNumber;
    private String livingArea;
    private LocalDate dateOfBirth;
    private String email; // optional

    // Doctor who registered this patient
    private String doctorEmail;
}
