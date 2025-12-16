package com.example.Medical.Service;

import com.example.Medical.model.Patient;
import com.example.Medical.Repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public Patient registerPatient(Patient request, String doctorEmail) {
        // Prevent duplicate by NIC if present
        if (request.getNic() != null) {
            Optional<Patient> existing = patientRepository.findByNic(request.getNic());
            if (existing.isPresent()) {
                return existing.get();
            }
        }

        Patient patient = Patient.builder()
                .nic(request.getNic())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .contactNumber(request.getContactNumber())
                .livingArea(request.getLivingArea())
                .dateOfBirth(request.getDateOfBirth())
                .email(request.getEmail())
                .doctorEmail(doctorEmail)
                .build();

        return patientRepository.save(patient);
    }

    public List<Patient> getPatientsForDoctor(String doctorEmail) {
        return patientRepository.findByDoctorEmail(doctorEmail);
    }

//    public Patient getPatientById(Long patientId, String doctorNic) {
//        return patientRepository.findById(patientId)
//                .filter(p -> p.getDoctorNic().equals(doctorEmail))
//                .orElseThrow(() -> new RuntimeException("Patient not found or not authorized"));
//    }
}
