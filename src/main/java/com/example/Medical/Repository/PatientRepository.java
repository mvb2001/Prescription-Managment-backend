package com.example.Medical.Repository;

import com.example.Medical.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByNic(String nic);
    List<Patient> findByDoctorEmail(String doctorEmail);
}
