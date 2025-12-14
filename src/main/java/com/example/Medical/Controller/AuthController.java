package com.example.Medical.Controller;

import com.example.Medical.DTO.AuthResponse;
import com.example.Medical.DTO.DoctorSignupRequest;
import com.example.Medical.DTO.LoginRequest;
import com.example.Medical.DTO.PharmacistSignupRequest;
import com.example.Medical.DTO.*;
import com.example.Medical.Service.AuthService;
import com.example.Medical.model.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup/doctor")
    public AuthResponse signupDoctor(@RequestBody DoctorSignupRequest request) {
        return authService.signupDoctor(request);
    }

    @PostMapping("/signup/pharmacist")
    public AuthResponse signupPharmacist(@RequestBody PharmacistSignupRequest request,
                                         Authentication authentication) {

        Doctor doctor = (Doctor) authentication.getPrincipal();

        return authService.signupPharmacist(request, doctor.getNic());
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
