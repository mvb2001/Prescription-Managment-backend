

package com.example.Medical.Controller;

import com.example.Medical.DTO.PrescriptionRequest;
import com.example.Medical.Service.PrescriptionService;
import com.example.Medical.model.Patient;
import com.example.Medical.model.Prescription;
import com.example.Medical.Service.PatientService;
import com.example.Medical.Service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final PrescriptionService prescriptionService;

    @PostMapping("/register")
    public Patient registerPatient(@RequestBody Patient patient, Authentication authentication) {
        String doctorEmail = authentication.getName();
        return patientService.registerPatient(patient, doctorEmail);
    }
    @PostMapping("/{patientId}/prescriptions")
    public Prescription createPrescription(
            @PathVariable Long patientId,
            @RequestBody PrescriptionRequest request,
            Authentication authentication) {

        
        String doctorEmail = authentication.getName();

        return prescriptionService.createPrescription(patientId, request, doctorEmail);
    }

    @GetMapping("/{patientId}/prescriptions")
    public List<Prescription> getPrescriptions(
            @PathVariable Long patientId,
            Authentication authentication) {

        
        String doctorEmail = authentication.getName();

        
        return prescriptionService.getPrescriptionsByPatientAndDoctor(patientId, doctorEmail);
    }

}
