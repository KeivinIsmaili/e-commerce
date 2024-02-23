package com.ecommerce.project.security.services.authentication;

import com.ecommerce.project.security.payload.request.LoginRequest;
import com.ecommerce.project.security.payload.request.SignupRequest;
import com.ecommerce.project.security.payload.response.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<JwtResponse> authenticateUser(LoginRequest loginRequest);

    ResponseEntity<?> registerUser(SignupRequest signupRequest);

}