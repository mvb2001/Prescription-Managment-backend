package com.example.Medical.Repository;

import com.example.Medical.model.PrescriptionPharmacist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionPharmacistRepository extends JpaRepository<PrescriptionPharmacist, Long> {
    List<PrescriptionPharmacist> findAllByPatientIdAndDoctorEmail(Long patientId, String doctorEmail);
    List<PrescriptionPharmacist> findAllByOrderByCreatedAtAsc();
    List<PrescriptionPharmacist> findAllByDoctorEmailOrderByCreatedAtAsc(String doctorEmail);
}
