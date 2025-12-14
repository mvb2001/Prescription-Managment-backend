package com.example.Medical.Repository;

import com.example.Medical.model.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PharmacistRepository extends JpaRepository<Pharmacist, String> {
    Optional<Pharmacist> findByEmail(String email);
}
