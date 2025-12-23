package com.example.Medical.Service;

import com.example.Medical.DTO.PrescriptionRequest;
import com.example.Medical.Repository.PatientRepository;
import com.example.Medical.Repository.PharmacistRepository;
import com.example.Medical.model.Patient;
import com.example.Medical.model.Pharmacist;
import com.example.Medical.model.Prescription;
import com.example.Medical.Repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PatientRepository patientRepository;
    private final PharmacistRepository pharmacistRepository;
    public Prescription createPrescription(Long patientId,
                                           PrescriptionRequest request,
                                           String doctorEmail) {

       
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

      
        Prescription prescription = Prescription.builder()
                .patient(patient)
                .doctorEmail(doctorEmail)
                .note(request.getNote())
                .bpLevel(request.getBpLevel())
                .fbsLevel(request.getFbsLevel())
                .testNeed(request.getTestNeed())
                .height(request.getHeight())
                .weight(request.getWeight())
                .bodyTemperature(request.getBodyTemperature())
                .heartRate(request.getHeartRate())
                .allergies(request.getAllergies())
                .medicineIssue(request.getMedicineIssue())
                .medicineToGet(request.getMedicineToGet())
                .otherNote(request.getOtherNote())
                .createdAt(LocalDateTime.now())
                .build();

      
        return prescriptionRepository.save(prescription);
    }
    public List<Prescription> getPrescriptionsByPatientAndDoctor(Long patientId, String doctorEmail) {
       
        return prescriptionRepository.findAllByPatientIdAndDoctorEmail(patientId, doctorEmail);
    }

    public List<Prescription> getAllPrescriptionsForPharmacist(String pharmacistEmail) {
        
        Pharmacist pharmacist = pharmacistRepository.findByEmail(pharmacistEmail)
                .orElseThrow(() -> new RuntimeException("Pharmacist not found"));
        
       
        String doctorEmail = pharmacist.getDoctor().getEmail();
        
       
        return prescriptionRepository.findAllByDoctorEmailOrderByCreatedAtAsc(doctorEmail);
    }
}

