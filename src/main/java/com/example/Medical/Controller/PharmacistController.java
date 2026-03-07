package com.example.Medical.Controller;

import com.example.Medical.Service.PrescriptionService;
import com.example.Medical.model.PrescriptionPharmacist;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/pharmacist")
@CrossOrigin(origins = "http://localhost:5173")
public class PharmacistController {

    private final PrescriptionService prescriptionService;

    public PharmacistController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GetMapping("/prescriptions")
    @PreAuthorize("hasRole('PHARMACIST')")
    public List<PrescriptionPharmacist> getPrescriptions(Authentication authentication) {
        String pharmacistEmail = authentication.getName();
        return prescriptionService.getAllPrescriptionsForPharmacist(pharmacistEmail);
    }
    
    @DeleteMapping("/prescriptions/{id}")
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<String> deletePrescription(@PathVariable Long id) {
        try {
            prescriptionService.deletePrescriptionForPharmacist(id);
            return ResponseEntity.ok("Prescription deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
