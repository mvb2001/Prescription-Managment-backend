package com.example.Medical.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "prescription")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(nullable = false)
    private String doctorEmail;

    private String note;
    private String bpLevel;
    private String fbsLevel;
    private String testNeed;
    private Double height;
    private Double weight;
    private Double bodyTemperature;
    private Integer heartRate;
    private String allergies;
    private String medicineIssue;
    private String medicineToGet;
    private String otherNote;

    private LocalDateTime createdAt;
}
