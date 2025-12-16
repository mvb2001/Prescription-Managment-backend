package com.example.Medical.Service;

import com.example.Medical.DTO.*;
import com.example.Medical.DTO.AuthResponse;
import com.example.Medical.DTO.DoctorSignupRequest;
import com.example.Medical.DTO.LoginRequest;
import com.example.Medical.DTO.PharmacistSignupRequest;
import com.example.Medical.Security.JwtService;
import com.example.Medical.model.Doctor;
import com.example.Medical.model.Pharmacist;
import com.example.Medical.Repository.DoctorRepository;
import com.example.Medical.Repository.PharmacistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final DoctorRepository doctorRepository;
    private final PharmacistRepository pharmacistRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse signupDoctor(DoctorSignupRequest request) {
        Doctor doctor = Doctor.builder()
                .nic(request.nic())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .contact(request.contact())
                .mbbsUniversity(request.mbbsUniversity())
                .speciality(request.speciality())
                .build();

        doctorRepository.save(doctor);
        String token = jwtService.generateToken(doctor);
        return new AuthResponse(token, "DOCTOR");
    }

    public AuthResponse signupPharmacist(PharmacistSignupRequest request, String doctorEmail) {
        Doctor doctor = doctorRepository.findByEmail(doctorEmail)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Pharmacist pharmacist = Pharmacist.builder()
                .nic(request.nic())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .contact(request.contact())
                .doctor(doctor)
                .build();

        pharmacistRepository.save(pharmacist);
        String token = jwtService.generateToken(pharmacist);
        return new AuthResponse(token, "PHARMACIST");
    }

    public AuthResponse login(LoginRequest request) {
        // Try doctor first
        Doctor doctor = doctorRepository.findByEmail(request.email()).orElse(null);
        if (doctor != null) {
            if (!passwordEncoder.matches(request.password(), doctor.getPassword()))
                throw new RuntimeException("Invalid credentials");
            return new AuthResponse(jwtService.generateToken(doctor), "DOCTOR");
        }

        // Try pharmacist
        Pharmacist pharmacist = pharmacistRepository.findByEmail(request.email()).orElse(null);
        if (pharmacist != null) {
            if (!passwordEncoder.matches(request.password(), pharmacist.getPassword()))
                throw new RuntimeException("Invalid credentials");
            return new AuthResponse(jwtService.generateToken(pharmacist), "PHARMACIST");
        }

        throw new RuntimeException("User not found");
    }
}
