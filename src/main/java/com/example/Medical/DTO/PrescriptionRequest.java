package com.example.Medical.DTO;

import lombok.Data;

@Data
public class PrescriptionRequest {
    private Long patientId;
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
}
