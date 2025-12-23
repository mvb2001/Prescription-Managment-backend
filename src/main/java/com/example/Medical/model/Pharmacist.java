package com.example.Medical.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pharmacist")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pharmacist {

    @Id
    private String nic;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;
    private String contact;

    @ManyToOne
    @JoinColumn(name = "doctor_email", referencedColumnName = "email")
    private Doctor doctor;
}
