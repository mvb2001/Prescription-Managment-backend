package com.example.Medical.Repository;

import com.example.Medical.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findAllByPatientIdAndDoctorEmail(Long patientId, String doctorEmail);
    List<Prescription> findAllByOrderByCreatedAtAsc();
    List<Prescription> findAllByDoctorEmailOrderByCreatedAtAsc(String doctorEmail);
}

